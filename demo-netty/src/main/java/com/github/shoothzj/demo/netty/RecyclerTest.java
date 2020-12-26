package com.github.shoothzj.demo.netty;

import io.netty.util.Recycler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class RecyclerTest {

    private static Recycler<RecycleDto> recycler = new Recycler<RecycleDto>() {
        @Override
        protected RecycleDto newObject(Handle<RecycleDto> handle) {
            return new RecycleDto(handle);
        }
    };

    static class RecycleDto {

        Recycler.Handle<RecycleDto> handle;

        public RecycleDto(Recycler.Handle<RecycleDto> handle) {
            this.handle = handle;
        }

        public void recycle() {
            handle.recycle(this);
        }

    }

    public static void main(String[] args) {
        final RecycleDto recycleDto = recycler.get();
        recycleDto.recycle();
        final RecycleDto recycleDto2 = recycler.get();
        System.out.println(recycleDto == recycleDto2);
    }


}
