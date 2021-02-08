package com.github.shoothzj.demo.trouble.shoot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.client.LedgerMetadataBuilder;
import org.apache.bookkeeper.client.api.DigestType;
import org.apache.bookkeeper.client.api.LedgerMetadata;
import org.apache.bookkeeper.net.BookieId;
import org.apache.bookkeeper.proto.DataFormats;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author hezhangjian
 */
@Slf4j
public class BkUtil {

    public static void readLedger(long ledgerId, byte[] bytes) throws Exception {
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes)) {
            int metadataFormatVersion = readHeader(is);
            if (log.isDebugEnabled()) {
                String contentStr = "";
                log.debug("Format version {} detected{}", metadataFormatVersion, contentStr);
            }

            switch (metadataFormatVersion) {
                case 3:
                    parseVersion3Config(ledgerId, is, Optional.empty());
                default:
                    break;
            }
        }
    }

    private static LedgerMetadata parseVersion3Config(long ledgerId, InputStream is, Optional<Long> metadataStoreCtime)
            throws IOException {
        LedgerMetadataBuilder builder = LedgerMetadataBuilder.create()
                .withId(ledgerId)
                .withMetadataFormatVersion(3);
        DataFormats.LedgerMetadataFormat.Builder formatBuilder = DataFormats.LedgerMetadataFormat.newBuilder();
        formatBuilder.mergeDelimitedFrom(is);
        DataFormats.LedgerMetadataFormat data = formatBuilder.build();
        decodeFormat(data, builder);
        if (data.hasCtime()) {
            builder.storingCreationTime(true);
        } else if (metadataStoreCtime.isPresent()) {
            builder.withCreationTime(metadataStoreCtime.get()).storingCreationTime(false);
        }
        final LedgerMetadata ledgerMetadata = builder.build();
        log.info("ledger metadata is [{}]", ledgerMetadata);
        log.info("====================");
        log.info("ledger ensembles is [{}]", ledgerMetadata.getAllEnsembles());
        log.info("ledger length is [{}]", ledgerMetadata.getLength());
        log.info("ledger last entry id is [{}]", ledgerMetadata.getLastEntryId());
        return ledgerMetadata;
    }

    private static void decodeFormat(DataFormats.LedgerMetadataFormat data, LedgerMetadataBuilder builder) throws IOException {
        builder.withEnsembleSize(data.getEnsembleSize());
        builder.withWriteQuorumSize(data.getQuorumSize());
        if (data.hasAckQuorumSize()) {
            builder.withAckQuorumSize(data.getAckQuorumSize());
        } else {
            builder.withAckQuorumSize(data.getQuorumSize());
        }

        if (data.hasCtime()) {
            builder.withCreationTime(data.getCtime());
        }

        if (data.getState() == DataFormats.LedgerMetadataFormat.State.IN_RECOVERY) {
            builder.withInRecoveryState();
        } else if (data.getState() == DataFormats.LedgerMetadataFormat.State.CLOSED) {
            builder.withClosedState().withLastEntryId(data.getLastEntryId()).withLength(data.getLength());
        }

        if (data.hasPassword()) {
            builder.withPassword(data.getPassword().toByteArray())
                    .withDigestType(protoToApiDigestType(data.getDigestType()));
        }

        for (DataFormats.LedgerMetadataFormat.Segment s : data.getSegmentList()) {
            List<BookieId> addrs = new ArrayList<>();
            for (String addr : s.getEnsembleMemberList()) {
                addrs.add(BookieId.parse(addr));
            }
            builder.newEnsembleEntry(s.getFirstEntryId(), addrs);
        }

        if (data.getCustomMetadataCount() > 0) {
            builder.withCustomMetadata(data.getCustomMetadataList().stream().collect(
                    Collectors.toMap(e -> e.getKey(),
                            e -> e.getValue().toByteArray())));
        }

        if (data.hasCToken()) {
            builder.withCToken(data.getCToken());
        }
    }

    private static DigestType protoToApiDigestType(DataFormats.LedgerMetadataFormat.DigestType digestType) {
        switch (digestType) {
            case HMAC:
                return DigestType.MAC;
            case CRC32:
                return DigestType.CRC32;
            case CRC32C:
                return DigestType.CRC32C;
            case DUMMY:
                return DigestType.DUMMY;
            default:
                throw new IllegalArgumentException("Unable to convert digest type " + digestType);
        }
    }

    private static final String LINE_SPLITTER = "\n";
    private static final byte[] LINE_SPLITTER_BYTES = LINE_SPLITTER.getBytes(UTF_8);
    private static final byte[] VERSION_KEY_BYTES = "BookieMetadataFormatVersion\t".getBytes(UTF_8);
    private static final int MAX_VERSION_DIGITS = 10;

    private static int readHeader(InputStream is) throws IOException {
        checkState(LINE_SPLITTER_BYTES.length == 1, "LINE_SPLITTER must be single byte");

        for (int i = 0; i < VERSION_KEY_BYTES.length; i++) {
            int b = is.read();
            if (b < 0 || ((byte) b) != VERSION_KEY_BYTES[i]) {
                throw new IOException("Ledger metadata header corrupt at index " + i);
            }
        }
        byte[] versionBuf = new byte[MAX_VERSION_DIGITS];
        int i = 0;
        while (i < MAX_VERSION_DIGITS) {
            int b = is.read();
            if (b == LINE_SPLITTER_BYTES[0]) {
                String versionStr = new String(versionBuf, 0, i, UTF_8);
                try {
                    return Integer.parseInt(versionStr);
                } catch (NumberFormatException nfe) {
                    throw new IOException("Unable to parse version number from " + versionStr);
                }
            } else if (b < 0) {
                break;
            } else {
                versionBuf[i++] = (byte) b;
            }
        }
        throw new IOException("Unable to find end of version number, metadata appears corrupt");
    }

}
