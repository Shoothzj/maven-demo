grammar prometheus;

prometheus: metricName dimensions* ' ' value (' 'timestamp)*;
metricName: NAME+;
dimensions: '{' (dimension',')* dimension '}';
dimension: NAME':"'NAME':"';
value: NAME+;
timestamp: DIGIT+;


fragment NAME: [a-zA-Z0-9];
fragment DIGIT: [0-9];