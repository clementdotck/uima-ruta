PACKAGE uima.textmarker;


DECLARE BeforeMe;

(NUM (PERIOD | SPECIAL) NUM (PERIOD | SPECIAL) NUM){OR(ExampleCondition("24.02.1981", "dd.mm.yyyy"),
     ExampleCondition("1981/02/24", "yyyy/mm/dd")) -> MARK(BeforeMe)};