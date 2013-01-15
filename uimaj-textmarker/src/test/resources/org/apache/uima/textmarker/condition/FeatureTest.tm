PACKAGE org.apache.uima;

DECLARE T1, T2, T3, T4, T5, T6, T7, T8, T9;

DECLARE Annotation FS (STRING string, DOUBLE double, INT int, BOOLEAN boolean);


Document{-> CREATE(FS, "string" = "string", "double" = 0.5, "int" = 2, "boolean" = true)};

FS{FEATURE("string", "string") -> MARK(T1)};
FS{FEATURE("double", 0.5) -> MARK(T2)};
FS{FEATURE("int", 2) -> MARK(T3)};
FS{FEATURE("boolean", true) -> MARK(T4)};

FS{FEATURE("string", "") -> MARK(T5)};
FS{FEATURE("double", 0.6) -> MARK(T5)};
FS{FEATURE("int", 3) -> MARK(T5)};
FS{FEATURE("boolean", false) -> MARK(T5)};