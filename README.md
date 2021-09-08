# Notion SDK / Kotlin Multiplatform
![cover](.github/img/cover.png)

<p align="center">Use your Notion table as a data source</p>

---

![Build](https://github.com/notionsdk/notion-sdk-kotlin/workflows/Release/badge.svg) [![API version](https://img.shields.io/badge/API%20version-20210816-blue?style=flat&logo=notion&logoColor=white)](https://developers.notion.com/changelog)
[![GitHub license](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat)](https://github.com/notionsdk/notion-sdk-kotlin/blob/master/LICENSE)

Not affiliated with Notion but uses the official API.<br>
The documentation is available here: https://developers.notion.com

### Installation
```kotlin
implementation("com.petersamokhin.notionsdk:notionsdk:$latestVersion")
```
Library is published to the Maven Central repository.

Latest version:  [![maven-central](https://img.shields.io/badge/Maven%20Central-0.0.1-yellowgreen?style=flat)](https://search.maven.org/search?q=g:com.petersamokhin.notionsdk)

### Supported endpoints
- [`/databases/:id/query`](https://developers.notion.com/reference/retrieve-a-database)
- [`/databases/:id`](https://developers.notion.com/reference/post-database-query)

### Example usage
How to get a token and the database ID: https://developers.notion.com/docs/getting-started

<img src=".github/img/table.png" width="373" height="187"/>

```kotlin
val notion = Notion.fromToken(
    token = "token",
    httpClient = HttpClient(CIO)
)

val schema = notion.retrieveDatabase("databaseId")
val database = notion.queryDatabase("databaseId")

val uncheckedRowSelectedOptionsIds = database.rows
    .first { row ->
        val checkboxColumnSelected = (row.columns
            .getValue("CheckboxColumn")
            .value as NotionDatabaseProperty.Checkbox)
            .selected

        !checkboxColumnSelected
    }
    .columns.getValue("MultiSelectColumn")
    .let { column -> column.value as NotionDatabaseProperty.MultiSelect }
    .selected.map { option -> option.id }

val availableOptions = databaseInfo.schema.getValue("MultiSelectColumn")
    .let { property -> property as NotionDatabasePropertySchema.MultiSelect }
    .options

val uncheckedRowIgnoredOptionsNames = availableOptions
    .filter { option -> option.id !in uncheckedRowSelectedOptionsIds }
    .map { option -> option.name }

println(uncheckedRowIgnoredOptionsNames)
// Output:
// [OptionThree]

println(database)
// Output:
// NotionDatabase(
//    rows=[
//        NotionDatabaseRow(
//            columns={
//                MultiSelectColumn=NotionDatabaseColumn(key=MultiSelectColumn, value=MultiSelect(id=MWKa, selected=[])),
//                CheckboxColumn=NotionDatabaseColumn(key=CheckboxColumn, value=Checkbox(id=%5CUbj, selected=true)),
//                LastEditedByColumn=NotionDatabaseColumn(key=LastEditedByColumn, value=LastEditedBy(id=d%3EIW, lastEditedBy=User(id=UUID, name=Peter Samokhin, avatarUrl=https://site.com/whatever.png, email=contact+notionsdk@petersamokhin.com))), 
//                TitleColumn=NotionDatabaseColumn(key=TitleColumn, value=Title(id=title, text=second row title))
//            }
//        ),
//    
//        NotionDatabaseRow(
//            columns={
//                MultiSelectColumn=NotionDatabaseColumn(key=MultiSelectColumn, value=MultiSelect(id=MWKa, selected=[Option(id=UUID, name=OptionOne), Option(id=UUID, name=OptionTwo)])),
//                CheckboxColumn=NotionDatabaseColumn(key=CheckboxColumn, value=Checkbox(id=%5CUbj, selected=false)),
//                LastEditedByColumn=NotionDatabaseColumn(key=LastEditedByColumn, value=LastEditedBy(id=d%3EIW, lastEditedBy=User(id=UUID, name=Peter Samokhin, avatarUrl=https://site.com/whatever.png, email=contact+notionsdk@petersamokhin.com))), 
//                TitleColumn=NotionDatabaseColumn(key=TitleColumn, value=Title(id=title, text=first row title))
//            }
//        )
//    ],
//    nextCursor=null,
//    hasMore=false
// )
```

### Purpose of the SDK
This library is intended to help with retrieving the info from the Notion databases.<br>
Mostly, this is a handy tool which covers only the specific needs if you use Notion as a convenient data source.<br>
Thus some additional information like the text styles and some other properties are omitted.<br>
Only properties (of all types) and the database schema is supported.<br>

### Feel free to contribute if you need some additional functionality