package tmg.flashback.ui.components.flag

val countryCodesMap: Map<String, String> = mapOf(
    "AFG" to "AF", // Afghanistan
    "ALA" to "AX", // Åland Islands
    "ALB" to "AL", // Albania
    "DZA" to "DZ", // Algeria
    "ASM" to "AS", // American Samoa
    "AND" to "AD", // Andorra
    "AGO" to "AO", // Angola
    "AIA" to "AI", // Anguilla
    "ATA" to "AQ", // Antarctica
    "ATG" to "AG", // Antigua and Barbuda
    "ARG" to "AR", // Argentina
    "ARM" to "AM", // Armenia
    "ABW" to "AW", // Aruba
    "AUS" to "AU", // Australia
    "AUT" to "AT", // Austria
    "AZE" to "AZ", // Azerbaijan
    "BHS" to "BS", // Bahamas
    "BHR" to "BH", // Bahrain
    "BGD" to "BD", // Bangladesh
    "BRB" to "BB", // Barbados
    "BLR" to "BY", // Belarus
    "BEL" to "BE", // Belgium
    "BLZ" to "BZ", // Belize
    "BEN" to "BJ", // Benin
    "BMU" to "BM", // Bermuda
    "BTN" to "BT", // Bhutan
    "BOL" to "BO", // Bolivia (Plurinational State of)
    "BES" to "BQ", // Bonaire, Sint Eustatius and Saba
    "BIH" to "BA", // Bosnia and Herzegovina
    "BWA" to "BW", // Botswana
    "BVT" to "BV", // Bouvet Island
    "BRA" to "BR", // Brazil
    "IOT" to "IO", // British Indian Ocean Territory
    "BRN" to "BN", // Brunei Darussalam
    "BGR" to "BG", // Bulgaria
    "BFA" to "BF", // Burkina Faso
    "BDI" to "BI", // Burundi
    "CPV" to "CV", // Cabo Verde
    "KHM" to "KH", // Cambodia
    "CMR" to "CM", // Cameroon
    "CAN" to "CA", // Canada
    "CYM" to "KY", // Cayman Islands
    "CAF" to "CF", // Central African Republic
    "TCD" to "TD", // Chad
    "CHL" to "CL", // Chile
    "CHN" to "CN", // China
    "CXR" to "CX", // Christmas Island
    "CCK" to "CC", // Cocos (Keeling) Islands
    "COL" to "CO", // Colombia
    "COM" to "KM", // Comoros
    "COG" to "CG", // Congo
    "COD" to "CD", // Congo (Democratic Republic of the)
    "COK" to "CK", // Cook Islands
    "CRI" to "CR", // Costa Rica
    "CIV" to "CI", // Côte d'Ivoire
    "HRV" to "HR", // Croatia
    "CUB" to "CU", // Cuba
    "CUW" to "CW", // Curaçao
    "CYP" to "CY", // Cyprus
    "CZE" to "CZ", // Czechia
    "DNK" to "DK", // Denmark
    "DJI" to "DJ", // Djibouti
    "DMA" to "DM", // Dominica
    "DOM" to "DO", // Dominican Republic
    "ECU" to "EC", // Ecuador
    "EGY" to "EG", // Egypt
    "SLV" to "SV", // El Salvador
    "GNQ" to "GQ", // Equatorial Guinea
    "ERI" to "ER", // Eritrea
    "EST" to "EE", // Estonia
    "SWZ" to "SZ", // Eswatini
    "ETH" to "ET", // Ethiopia
    "FLK" to "FK", // Falkland Islands (Malvinas)
    "FRO" to "FO", // Faroe Islands
    "FJI" to "FJ", // Fiji
    "FIN" to "FI", // Finland
    "FRA" to "FR", // France
    "GUF" to "GF", // French Guiana
    "PYF" to "PF", // French Polynesia
    "ATF" to "TF", // French Southern Territories
    "GAB" to "GA", // Gabon
    "GMB" to "GM", // Gambia
    "GEO" to "GE", // Georgia
    "DEU" to "DE", // Germany
    "GHA" to "GH", // Ghana
    "GIB" to "GI", // Gibraltar
    "GRC" to "GR", // Greece
    "GRL" to "GL", // Greenland
    "GRD" to "GD", // Grenada
    "GLP" to "GP", // Guadeloupe
    "GUM" to "GU", // Guam
    "GTM" to "GT", // Guatemala
    "GGY" to "GG", // Guernsey
    "GIN" to "GN", // Guinea
    "GNB" to "GW", // Guinea-Bissau
    "GUY" to "GY", // Guyana
    "HTI" to "HT", // Haiti
    "HMD" to "HM", // Heard Island and McDonald Islands
    "VAT" to "VA", // Holy See
    "HND" to "HN", // Honduras
    "HKG" to "HK", // Hong Kong
    "HUN" to "HU", // Hungary
    "ISL" to "IS", // Iceland
    "IND" to "IN", // India
    "IDN" to "ID", // Indonesia
    "IRN" to "IR", // Iran (Islamic Republic of)
    "IRQ" to "IQ", // Iraq
    "IRL" to "IE", // Ireland
    "IMN" to "IM", // Isle of Man
    "ISR" to "IL", // Israel
    "ITA" to "IT", // Italy
    "JAM" to "JM", // Jamaica
    "JPN" to "JP", // Japan
    "JEY" to "JE", // Jersey
    "JOR" to "JO", // Jordan
    "KAZ" to "KZ", // Kazakhstan
    "KEN" to "KE", // Kenya
    "KIR" to "KI", // Kiribati
    "PRK" to "KP", // Korea (Democratic People's Republic of)
    "KOR" to "KR", // Korea (Republic of)
    "KWT" to "KW", // Kuwait
    "KGZ" to "KG", // Kyrgyzstan
    "LAO" to "LA", // Lao People's Democratic Republic
    "LVA" to "LV", // Latvia
    "LBN" to "LB", // Lebanon
    "LSO" to "LS", // Lesotho
    "LBR" to "LR", // Liberia
    "LBY" to "LY", // Libya
    "LIE" to "LI", // Liechtenstein
    "LTU" to "LT", // Lithuania
    "LUX" to "LU", // Luxembourg
    "MAC" to "MO", // Macao
    "MKD" to "MK", // North Macedonia
    "MDG" to "MG", // Madagascar
    "MWI" to "MW", // Malawi
    "MYS" to "MY", // Malaysia
    "MDV" to "MV", // Maldives
    "MLI" to "ML", // Mali
    "MLT" to "MT", // Malta
    "MHL" to "MH", // Marshall Islands
    "MTQ" to "MQ", // Martinique
    "MRT" to "MR", // Mauritania
    "MUS" to "MU", // Mauritius
    "MYT" to "YT", // Mayotte
    "MEX" to "MX", // Mexico
    "FSM" to "FM", // Micronesia (Federated States of)
    "MDA" to "MD", // Moldova (Republic of)
    "MCO" to "MC", // Monaco
    "MNG" to "MN", // Mongolia
    "MNE" to "ME", // Montenegro
    "MSR" to "MS", // Montserrat
    "MAR" to "MA", // Morocco
    "MOZ" to "MZ", // Mozambique
    "MMR" to "MM", // Myanmar
    "NAM" to "NA", // Namibia
    "NRU" to "NR", // Nauru
    "NPL" to "NP", // Nepal
    "NLD" to "NL", // Netherlands
    "NCL" to "NC", // New Caledonia
    "NZL" to "NZ", // New Zealand
    "NIC" to "NI", // Nicaragua
    "NER" to "NE", // Niger
    "NGA" to "NG", // Nigeria
    "NIU" to "NU", // Niue
    "NFK" to "NF", // Norfolk Island
    "MNP" to "MP", // Northern Mariana Islands
    "NOR" to "NO", // Norway
    "OMN" to "OM", // Oman
    "PAK" to "PK", // Pakistan
    "PLW" to "PW", // Palau
    "PSE" to "PS", // Palestine, State of
    "PAN" to "PA", // Panama
    "PNG" to "PG", // Papua New Guinea
    "PRY" to "PY", // Paraguay
    "PER" to "PE", // Peru
    "PHL" to "PH", // Philippines
    "PCN" to "PN", // Pitcairn
    "POL" to "PL", // Poland
    "PRT" to "PT", // Portugal
    "PRI" to "PR", // Puerto Rico
    "QAT" to "QA", // Qatar
    "REU" to "RE", // Réunion
    "ROU" to "RO", // Romania
    "RUS" to "RU", // Russian Federation
    "RWA" to "RW", // Rwanda
    "BLM" to "BL", // Saint Barthélemy
    "SHN" to "SH", // Saint Helena, Ascension and Tristan da Cunha
    "KNA" to "KN", // Saint Kitts and Nevis
    "LCA" to "LC", // Saint Lucia
    "MAF" to "MF", // Saint Martin (French part)
    "SPM" to "PM", // Saint Pierre and Miquelon
    "VCT" to "VC", // Saint Vincent and the Grenadines
    "WSM" to "WS", // Samoa
    "SMR" to "SM", // San Marino
    "STP" to "ST", // Sao Tome and Principe
    "SAU" to "SA", // Saudi Arabia
    "SEN" to "SN", // Senegal
    "SRB" to "RS", // Serbia
    "SYC" to "SC", // Seychelles
    "SLE" to "SL", // Sierra Leone
    "SGP" to "SG", // Singapore
    "SXM" to "SX", // Sint Maarten (Dutch part)
    "SVK" to "SK", // Slovakia
    "SVN" to "SI", // Slovenia
    "SLB" to "SB", // Solomon Islands
    "SOM" to "SO", // Somalia
    "ZAF" to "ZA", // South Africa
    "SGS" to "GS", // South Georgia and the South Sandwich Islands
    "SSD" to "SS", // South Sudan
    "ESP" to "ES", // Spain
    "LKA" to "LK", // Sri Lanka
    "SDN" to "SD", // Sudan
    "SUR" to "SR", // Suriname
    "SJM" to "SJ", // Svalbard and Jan Mayen
    "SWE" to "SE", // Sweden
    "CHE" to "CH", // Switzerland
    "SYR" to "SY", // Syrian Arab Republic
    "TWN" to "TW", // Taiwan, Province of China
    "TJK" to "TJ", // Tajikistan
    "TZA" to "TZ", // Tanzania, United Republic of
    "THA" to "TH", // Thailand
    "TLS" to "TL", // Timor-Leste
    "TGO" to "TG", // Togo
    "TKL" to "TK", // Tokelau
    "TON" to "TO", // Tonga
    "TTO" to "TT", // Trinidad and Tobago
    "TUN" to "TN", // Tunisia
    "TUR" to "TR", // Turkey
    "TKM" to "TM", // Turkmenistan
    "TCA" to "TC", // Turks and Caicos Islands
    "TUV" to "TV", // Tuvalu
    "UGA" to "UG", // Uganda
    "UKR" to "UA", // Ukraine
    "ARE" to "AE", // United Arab Emirates
    "GBR" to "GB", // United Kingdom of Great Britain and Northern Ireland
    "USA" to "US", // United States of America
    "UMI" to "UM", // United States Minor Outlying Islands
    "URY" to "UY", // Uruguay
    "UZB" to "UZ", // Uzbekistan
    "VUT" to "VU", // Vanuatu
    "VEN" to "VE", // Venezuela (Bolivarian Republic of)
    "VNM" to "VN", // Viet Nam
    "VGB" to "VG", // Virgin Islands (British)
    "VIR" to "VI", // Virgin Islands (U.S.)
    "WLF" to "WF", // Wallis and Futuna
    "ESH" to "EH", // Western Sahara
    "YEM" to "YE", // Yemen
    "ZMB" to "ZM", // Zambia
    "ZWE" to "ZW"  // Zimbabwe
)