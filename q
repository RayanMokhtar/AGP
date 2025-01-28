[33mcommit ae699b289c97e51ecf3cb9ad8a3a812266a12633[m[33m ([m[1;31morigin/template[m[33m)[m
Author: Walliodasu <96730865+Walliodasu@users.noreply.github.com>
Date:   Tue Jan 28 00:45:48 2025 +0100

    v4
    
    paths relatifs

[1mdiff --git a/AGP_DB/Index/_4.si b/AGP_DB/Index/_4.si[m
[1mdeleted file mode 100644[m
[1mindex 809e5db..0000000[m
Binary files a/AGP_DB/Index/_4.si and /dev/null differ
[1mdiff --git a/AGP_DB/Index/_4.cfe b/AGP_DB/Index/_6.cfe[m
[1msimilarity index 82%[m
[1mrename from AGP_DB/Index/_4.cfe[m
[1mrename to AGP_DB/Index/_6.cfe[m
[1mindex c104adc..1eab25c 100644[m
Binary files a/AGP_DB/Index/_4.cfe and b/AGP_DB/Index/_6.cfe differ
[1mdiff --git a/AGP_DB/Index/_4.cfs b/AGP_DB/Index/_6.cfs[m
[1msimilarity index 85%[m
[1mrename from AGP_DB/Index/_4.cfs[m
[1mrename to AGP_DB/Index/_6.cfs[m
[1mindex 01fb674..cf2f9e1 100644[m
Binary files a/AGP_DB/Index/_4.cfs and b/AGP_DB/Index/_6.cfs differ
[1mdiff --git a/AGP_DB/Index/_6.si b/AGP_DB/Index/_6.si[m
[1mnew file mode 100644[m
[1mindex 0000000..97d5a35[m
Binary files /dev/null and b/AGP_DB/Index/_6.si differ
[1mdiff --git a/AGP_DB/Index/segments_5 b/AGP_DB/Index/segments_5[m
[1mdeleted file mode 100644[m
[1mindex 9781fbb..0000000[m
Binary files a/AGP_DB/Index/segments_5 and /dev/null differ
[1mdiff --git a/AGP_DB/Index/segments_7 b/AGP_DB/Index/segments_7[m
[1mnew file mode 100644[m
[1mindex 0000000..a279717[m
Binary files /dev/null and b/AGP_DB/Index/segments_7 differ
[1mdiff --git a/bin/persistence/Database.class b/bin/persistence/Database.class[m
[1mindex fcd1798..6c4cdef 100644[m
Binary files a/bin/persistence/Database.class and b/bin/persistence/Database.class differ
[1mdiff --git a/bin/tests/DemoApiBDA.class b/bin/tests/DemoApiBDA.class[m
[1mindex 91ec6c2..1097ab4 100644[m
Binary files a/bin/tests/DemoApiBDA.class and b/bin/tests/DemoApiBDA.class differ
[1mdiff --git a/src/persistence/Database.java b/src/persistence/Database.java[m
[1mindex b712325..c630ee1 100644[m
[1m--- a/src/persistence/Database.java[m
[1m+++ b/src/persistence/Database.java[m
[36m@@ -24,8 +24,8 @@[m [mpublic class Database {[m
 	private static String password = "Pmlpmlpmlk000";[m
 	[m
 	// Textual queries configuration[m
[31m-	private static Path sourcePath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Description");[m
[31m-	private static Path indexPath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Index");[m
[32m+[m	[32mprivate static Path sourcePath = Paths.get("AGP_DB", "Description");[m
[32m+[m	[32mprivate static Path indexPath = Paths.get("AGP_DB", "Index");[m
 	private static String table = "Place";[m
 	private static String joinKey = "id";[m
 	[m
[1mdiff --git a/src/tests/DemoApiBDA.java b/src/tests/DemoApiBDA.java[m
[1mindex 5946def..7fb66e8 100644[m
[1m--- a/src/tests/DemoApiBDA.java[m
[1m+++ b/src/tests/DemoApiBDA.java[m
[36m@@ -27,8 +27,8 @@[m [mpublic class DemoApiBDA {[m
 	 * @param args[m
 	 */[m
 	public static void main(String[] args) {[m
[31m-		Path sourcePath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Description");[m
[31m-		Path indexPath = Paths.get("C:\\Users\\darkf\\Desktop\\java_workspace\\AGP\\AGP_DB\\Index");[m
[32m+[m		[32mPath sourcePath = Paths.get("AGP_DB", "Description");[m
[32m+[m		[32mPath indexPath = Paths.get("AGP_DB", "Index");[m
 		[m
 		try {[m
 			// Create an index and add documents[m
