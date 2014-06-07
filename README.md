# Before string-format...

There are a few situations in which you need to construct a big string with a lot of arguments.
In these situations, one of your options is to concatenate each part like this:

```java
String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_LATITUDE + ", " + COLUMN_LONGITUDE + " " +
                        "FROM " + TABLE_NAME + " " +
                        "WHERE " + COLUMN_NAME + " LIKE " + search + " " +
                        "ORDER BY " + COLUMN_NAME + " LIMIT " + maxResult + " OFFSET " + from;
```

**This is bad.** You have no overview of the string, there's a lot of boilerplate code in it. Moreover, if you need to change the position of any element you need to take care of this boilerplate code. Another solution, more elegant:

```java
String query = String.format("SELECT %s, %s, %s, %s " +
                        "FROM %s " +
                        "WHERE %s LIKE %s " +
                        "ORDER BY %s LIMIT %s OFFSET %s",
                        COLUMN_ID, COLUMN_NAME, COLUMN_LATITUDE, COLUMN_LONGITUDE, 
                        TABLE_NAME, COLUMN_NAME, COLUMN_NAME, search, maxResult, from);
```

**This is still bad.** The readability is better yet not optimal, you don't know what are these ```%s``` unless you count the following arguments. The same thing if you need to maintain this string.

# Meet string-format!

Here is a better solution, that improves readability and maintenance.

```java
String query = Strings.format(
			"SELECT {id}, {name}, {latitude}, {longitude} " +
					"FROM {table} " +
					"WHERE {search_field} LIKE {search_value} " +
					"ORDER BY {name} LIMIT {limit} OFFSET {offset}")
			.with("id", COLUMN_ID)
			.with("name", COLUMN_NAME)
			.with("latitude", COLUMN_LATITUDE)
			.with("longitude", COLUMN_LONGITUDE)
			.with("table", TABLE_NAME)
			.with("search_field", COLUMN_NAME)
			.with("search_value", search)
			.with("limit", maxResult)
			.with("offset", from)
			.build();
```

You can also use custom prefix and suffix.

```java
Strings.format("Hello [firstname] [lastname]!", "[", "]")
	.with("firstname", "John")
	.with("lastname", "Doe")
	.build();
```

> **NOTE:** If you forget an argument or add an extra argument that is not used in the string, it will raise an ```exception``` that tells you exactly what's wrong. See the [tests](https://github.com/JoanZapata/string-format/blob/master/src/test/java/com/joanzapata/utils/StringsTest.java#L23-L48) for more examples.

# Get it

[Download JAR](http://search.maven.org/remotecontent?filepath=com/joanzapata/utils/string-format/1.0.1/string-format-1.0.1.jar) or via **Maven Central**

```xml
<dependency>
    <groupId>com.joanzapata.utils</groupId>
    <artifactId>string-format</artifactId>
    <version>1.0.1</version>
</dependency>
```

# License

```
Copyright 2014 Joan Zapata

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
