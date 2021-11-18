# Change log

Version 0.0.5 *(2021-11-18)*
----------------------------
* Unify Notion icon model
* Add `icon` to the database rows

Version 0.0.4 *(2021-11-16)*
----------------------------
* Fix missing pagination logic for block children endpoint
* Fix indentation for the child pages content recursively rendered to Markdown
* Unify list result response data model

Version 0.0.3 *(2021-11-16)*
----------------------------
* [`/databases/:id/query`](https://developers.notion.com/reference/retrieve-a-database) JSON query as a raw string support
* Fix missing ID in the database rows (pages)
* Improve some public models contract

Version 0.0.2 *(2021-11-15)*
----------------------------

* [`/blocks/:id/children`](https://developers.notion.com/reference/retrieve-a-block) endpoint support
* [`/blocks/:id`](https://developers.notion.com/reference/get-block-children) endpoint support
* Add blocks to markdown exporter
* Bump dependencies

Version 0.0.1 *(2021-09-08)*
----------------------------

Initial release:
* [`/databases/:id/query`](https://developers.notion.com/reference/retrieve-a-database) endpoint support
* [`/databases/:id`](https://developers.notion.com/reference/post-database-query) endpoint support
* Don't support text styles and other properties, mainly support the database as a data source
* Library provides convenient mapped models to work with the database data
