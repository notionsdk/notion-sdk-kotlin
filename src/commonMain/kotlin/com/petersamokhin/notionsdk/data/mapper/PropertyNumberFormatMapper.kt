package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.response.RetrieveDatabaseSchemaProperty
import com.petersamokhin.notionsdk.data.model.result.NotionDatabasePropertySchema

internal fun RetrieveDatabaseSchemaProperty.Number.Format.toDomain(): NotionDatabasePropertySchema.Number.Format =
    when (this) {
        RetrieveDatabaseSchemaProperty.Number.Format.Number -> NotionDatabasePropertySchema.Number.Format.Number
        RetrieveDatabaseSchemaProperty.Number.Format.NumberWithCommas -> NotionDatabasePropertySchema.Number.Format.NumberWithCommas
        RetrieveDatabaseSchemaProperty.Number.Format.Percent -> NotionDatabasePropertySchema.Number.Format.Percent
        RetrieveDatabaseSchemaProperty.Number.Format.Dollar -> NotionDatabasePropertySchema.Number.Format.Dollar
        RetrieveDatabaseSchemaProperty.Number.Format.CanadianDollar -> NotionDatabasePropertySchema.Number.Format.CanadianDollar
        RetrieveDatabaseSchemaProperty.Number.Format.Euro -> NotionDatabasePropertySchema.Number.Format.Euro
        RetrieveDatabaseSchemaProperty.Number.Format.Pound -> NotionDatabasePropertySchema.Number.Format.Pound
        RetrieveDatabaseSchemaProperty.Number.Format.Yen -> NotionDatabasePropertySchema.Number.Format.Yen
        RetrieveDatabaseSchemaProperty.Number.Format.Ruble -> NotionDatabasePropertySchema.Number.Format.Ruble
        RetrieveDatabaseSchemaProperty.Number.Format.Rupee -> NotionDatabasePropertySchema.Number.Format.Rupee
        RetrieveDatabaseSchemaProperty.Number.Format.Won -> NotionDatabasePropertySchema.Number.Format.Won
        RetrieveDatabaseSchemaProperty.Number.Format.Yuan -> NotionDatabasePropertySchema.Number.Format.Yuan
        RetrieveDatabaseSchemaProperty.Number.Format.Real -> NotionDatabasePropertySchema.Number.Format.Real
        RetrieveDatabaseSchemaProperty.Number.Format.Lira -> NotionDatabasePropertySchema.Number.Format.Lira
        RetrieveDatabaseSchemaProperty.Number.Format.Rupiah -> NotionDatabasePropertySchema.Number.Format.Rupiah
        RetrieveDatabaseSchemaProperty.Number.Format.Franc -> NotionDatabasePropertySchema.Number.Format.Franc
        RetrieveDatabaseSchemaProperty.Number.Format.HongKongDollar -> NotionDatabasePropertySchema.Number.Format.HongKongDollar
        RetrieveDatabaseSchemaProperty.Number.Format.NewZealandDollar -> NotionDatabasePropertySchema.Number.Format.NewZealandDollar
        RetrieveDatabaseSchemaProperty.Number.Format.Krona -> NotionDatabasePropertySchema.Number.Format.Krona
        RetrieveDatabaseSchemaProperty.Number.Format.NorwegianKrone -> NotionDatabasePropertySchema.Number.Format.NorwegianKrone
        RetrieveDatabaseSchemaProperty.Number.Format.MexicanPeso -> NotionDatabasePropertySchema.Number.Format.MexicanPeso
        RetrieveDatabaseSchemaProperty.Number.Format.Rand -> NotionDatabasePropertySchema.Number.Format.Rand
        RetrieveDatabaseSchemaProperty.Number.Format.NewTaiwanDollar -> NotionDatabasePropertySchema.Number.Format.NewTaiwanDollar
        RetrieveDatabaseSchemaProperty.Number.Format.DanishKrone -> NotionDatabasePropertySchema.Number.Format.DanishKrone
        RetrieveDatabaseSchemaProperty.Number.Format.Zloty -> NotionDatabasePropertySchema.Number.Format.Zloty
        RetrieveDatabaseSchemaProperty.Number.Format.Baht -> NotionDatabasePropertySchema.Number.Format.Baht
        RetrieveDatabaseSchemaProperty.Number.Format.Forint -> NotionDatabasePropertySchema.Number.Format.Forint
        RetrieveDatabaseSchemaProperty.Number.Format.Koruna -> NotionDatabasePropertySchema.Number.Format.Koruna
        RetrieveDatabaseSchemaProperty.Number.Format.Shekel -> NotionDatabasePropertySchema.Number.Format.Shekel
        RetrieveDatabaseSchemaProperty.Number.Format.ChileanPeso -> NotionDatabasePropertySchema.Number.Format.ChileanPeso
        RetrieveDatabaseSchemaProperty.Number.Format.PhilippinePeso -> NotionDatabasePropertySchema.Number.Format.PhilippinePeso
        RetrieveDatabaseSchemaProperty.Number.Format.Dirham -> NotionDatabasePropertySchema.Number.Format.Dirham
        RetrieveDatabaseSchemaProperty.Number.Format.ColombianPeso -> NotionDatabasePropertySchema.Number.Format.ColombianPeso
        RetrieveDatabaseSchemaProperty.Number.Format.Riyal -> NotionDatabasePropertySchema.Number.Format.Riyal
        RetrieveDatabaseSchemaProperty.Number.Format.Ringgit -> NotionDatabasePropertySchema.Number.Format.Ringgit
        RetrieveDatabaseSchemaProperty.Number.Format.Leu -> NotionDatabasePropertySchema.Number.Format.Leu
    }