package tmg.flashback.feature.privacypolicy.presentation

fun getPolicy(email: String = "thementalgoose@gmail.com") = policy {
    h1("Flashback")
    text("""
        This privacy policy applies to the Flashback app (hereby referred to as "Application") for mobile devices
        that was created by Jordan Fisher (hereby referred to as "Service Provider") as a Free service. This
        service is intended for use "AS IS".
    """.trimIndent().replace("\n", ""))
    h2("Information Collection and Use")
    text("""
        The Application collects information when you download and use it. This information may include
        information such as
    """.trimIndent().replace("\n", ""))
    listItem("Google Play Services", "https://www.google.com/policies/privacy/")
    listItem("Firebase Analytics", "https://firebase.google.com/support/privacy/")
    listItem("Firebase Crashlytics", "https://firebase.google.com/support/privacy/")
    text("""
        The Service Provider may disclose User Provided and Automatically Collected Information:
    """.trimIndent())
    listItem("as required by law, such as to comply with a subpoena, or similar legal process;")
    listItem("""
        when they believe in good faith that disclosure is necessary to protect their rights, protect your
        safety or the safety of others, investigate fraud, or respond to a government request;
    """.trimIndent())
    listItem("""
        with their trusted services providers who work on their behalf, do not have an independent use of
        the information we disclose to them, and have agreed to adhere to the rules set forth in this
        privacy statement.
    """.trimIndent().replace("\n", ""))
    h2("Opt-Out Rights")
    text("""
        You can stop all collection of information by the Application easily by uninstalling it or disabling 
        "analytics" or "crash reporting" options in the settings. You may use the
        standard uninstall processes as may be available as part of your mobile device or via the mobile
        application marketplace or network.
    """.trimIndent().replace("\n", ""))
    h2("Data Retention Policy")
    text("""
        The Service Provider will retain User Provided data for as long as you use the Application and for a
        reasonable time thereafter. If you'd like them to delete User Provided Data that you have provided via
        the Application, please contact them at $email and they will respond in a reasonable
        time.
    """.trimIndent().replace("\n", ""))
    h2("Children")
    text("""
        The Service Provider does not use the Application to knowingly solicit data from or market to children
        under the age of 13.
    """.trimIndent().replace("\n", ""))
    text("""
        The Application does not address anyone under the age of 13. The Service Provider does not knowingly
        collect personally identifiable information from children under 13 years of age. In the case the
        Service Provider discover that a child under 13 has provided personal information, the Service
        Provider will immediately delete this from their servers. If you are a parent or guardian and you
        are aware that your child has provided us with personal information, please contact the Service
        Provider ($email) so that they will be able to take the necessary actions.
    """.trimIndent().replace("\n", ""))
    h2("Security")
    text("""
        The Service Provider is concerned about safeguarding the confidentiality of your information. The Service
        Provider provides physical, electronic, and procedural safeguards to protect information the Service
        Provider processes and maintains.
    """.trimIndent().replace("\n", ""))
    h2("Changes")
    text("""
        This Privacy Policy may be updated from time to time for any reason. The Service Provider will notify you
        of any changes to the Privacy Policy by updating this page with the new Privacy Policy. You are advised
        to consult this Privacy Policy regularly for any changes, as continued use is deemed approval of all
        changes.
    """.trimIndent().replace("\n", ""))
    h2("Your consent")
    text("""
        By using the Application, you are consenting to the processing of your information as set forth in this
        Privacy Policy now and as amended by us.
    """.trimIndent().replace("\n", ""))
    h2("Contact Us")
    text("""
        If you have any questions regarding privacy while using the Application, or have questions about the
        practices, please contact the Service Provider via email at $email
    """.trimIndent().replace("\n", ""))
}

fun policy(block: PolicyDSL.() -> Unit): Policy {
    val dsl = PolicyDSL()
    block(dsl)
    return Policy(dsl.elements.toList())
}

class PolicyDSL() {
    var elements: MutableList<PolicyElement> = mutableListOf()

    fun h1(text: String) {
        this.elements.add(PolicyElement.Header1(text))
    }
    fun h2(text: String) {
        this.elements.add(PolicyElement.Header2(text))
    }
    fun text(text: String) {
        this.elements.add(PolicyElement.Text(text))
    }
    fun listItem(text: String, link: String? = null) {
        this.elements.add(PolicyElement.ListItem(text, link))
    }
}

data class Policy(
    val elements: List<PolicyElement>
)

sealed interface PolicyElement {
    data class Header1(val text: String): PolicyElement
    data class Header2(val text: String): PolicyElement
    data class Text(val text: String): PolicyElement
    data class ListItem(val text: String, val link: String? = null): PolicyElement
}