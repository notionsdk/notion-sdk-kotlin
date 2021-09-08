package com.petersamokhin.notionsdk.data.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class NotionDatabaseSchema(
    val id: String,
    @SerialName("created_time")
    val createdTime: String,
    @SerialName("last_edited_time")
    val lastEditedTime: String,
    val title: String,
    val schema: Map<String, NotionDatabasePropertySchema>
)

@Serializable
public sealed class NotionDatabasePropertySchema {
    public abstract val id: String
    public abstract val name: String

    @Serializable
    @SerialName("title")
    public data class Title(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("rich_text")
    public data class Text(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("number")
    public data class Number(
        override val id: String,
        override val name: String,
        val format: Format
    ) : NotionDatabasePropertySchema() {
        @Serializable
        public enum class Format {
            @SerialName("number")
            Number,
            @SerialName("number_with_commas")
            NumberWithCommas,
            @SerialName("percent")
            Percent,
            @SerialName("dollar")
            Dollar,
            @SerialName("canadian_dollar")
            CanadianDollar,
            @SerialName("euro")
            Euro,
            @SerialName("pound")
            Pound,
            @SerialName("yen")
            Yen,
            @SerialName("ruble")
            Ruble,
            @SerialName("rupee")
            Rupee,
            @SerialName("won")
            Won,
            @SerialName("yuan")
            Yuan,
            @SerialName("real")
            Real,
            @SerialName("lira")
            Lira,
            @SerialName("rupiah")
            Rupiah,
            @SerialName("franc")
            Franc,
            @SerialName("hong_kong_dollar")
            HongKongDollar,
            @SerialName("new_zealand_dollar")
            NewZealandDollar,
            @SerialName("krona")
            Krona,
            @SerialName("norwegian_krone")
            NorwegianKrone,
            @SerialName("mexican_peso")
            MexicanPeso,
            @SerialName("rand")
            Rand,
            @SerialName("new_taiwan_dollar")
            NewTaiwanDollar,
            @SerialName("danish_krone")
            DanishKrone,
            @SerialName("zloty")
            Zloty,
            @SerialName("baht")
            Baht,
            @SerialName("forint")
            Forint,
            @SerialName("koruna")
            Koruna,
            @SerialName("shekel")
            Shekel,
            @SerialName("chilean_peso")
            ChileanPeso,
            @SerialName("philippine_peso")
            PhilippinePeso,
            @SerialName("dirham")
            Dirham,
            @SerialName("colombian_peso")
            ColombianPeso,
            @SerialName("riyal")
            Riyal,
            @SerialName("ringgit")
            Ringgit,
            @SerialName("leu")
            Leu
        }
    }

    @Serializable
    @SerialName("select")
    public data class Select(
        override val id: String,
        override val name: String,
        val options: List<Option>
    ) : NotionDatabasePropertySchema() {
        @Serializable
        public data class Option(
            val id: String,
            val name: String
        )
    }

    @Serializable
    @SerialName("multi_select")
    public data class MultiSelect(
        override val id: String,
        override val name: String,
        val options: List<Select.Option>
    ) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("formula")
    public data class Formula(
        override val id: String,
        override val name: String,
        val expression: String
    ) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("date")
    public data class Date(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("people")
    public data class People(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("files")
    public data class Files(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("checkbox")
    public data class Checkbox(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("url")
    public data class Url(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("email")
    public data class Email(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("phone_number")
    public data class PhoneNumber(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("created_time")
    public data class CreatedTime(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("created_by")
    public data class CreatedBy(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("last_edited_time")
    public data class LastEditedTime(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("last_edited_by")
    public data class LastEditedBy(override val id: String, override val name: String) : NotionDatabasePropertySchema()

    @Serializable
    @SerialName("rollup")
    public data class Rollup(override val id: String, override val name: String) : NotionDatabasePropertySchema()
}