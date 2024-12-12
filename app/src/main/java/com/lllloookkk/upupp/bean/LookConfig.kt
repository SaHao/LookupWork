package com.lllloookkk.upupp.bean

data class LookConfig(
    val code: Int,
    val data: Data
)

data class Data(
    val actions: Actions,
    val chatScript: ChatScript,
    val contact: Contact,
    val download: Download,
    val report: Report,
    val rtl: Boolean,
    val tips: Tips
)

data class Actions(
    val apply: String
)

data class ChatScript(
    val actions: ActionsX
)

data class Contact(
    val contents: List<String>
)

data class Download(
    val confirm: Confirm,
    val contents: List<String>
)

data class Report(
    val adjust: Adjust
)

data class Tips(
    val confirm: Confirm,
    val contents: List<String>
)

data class ActionsX(
    val contact: String
)

data class Confirm(
    val install: String
)

data class Adjust(
    val addtocart_tg: AddTg,
    val addtocart_ws: AddWs,
    val addtocart_ok: AddtocartOk,
    val addtocartlt: Addtocartlt,
    val addtocartpv: Addtocartpv,
    val app_show_app: AppShowApp,
    val click_download_btn: ClickDownloadBtn,
    val contact_show_popup: ContactShowPopup,
    val download_tip_show: DownloadTipShow,
    val jobs_show_card: JobsShowCard,
    val jobs_show_ptjob: JobsShowPtjob

)

data class AddTg(
    val code: String
)

data class AddWs(
    val code: String
)

data class AddtocartOk(
    val code: String
)

data class Addtocartlt(
    val code: String
)

data class Addtocartpv(
    val code: String
)

data class AppShowApp(
    val code: String
)

data class ClickDownloadBtn(
    val code: String
)

data class ContactShowPopup(
    val code: String
)

data class DownloadTipShow(
    val code: String
)

data class JobsShowCard(
    val code: String
)

data class JobsShowPtjob(
    val code: String
)