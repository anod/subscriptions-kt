package info.anodsplace.subscriptions.app

interface EditViewModel : ViewModel {
    val text: String
    fun onCloseClicked()
    fun onDoneChanged(value: Boolean)
    fun onTextChanged(value: String)
}