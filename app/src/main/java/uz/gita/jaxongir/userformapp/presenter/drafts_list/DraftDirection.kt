package uz.gita.jaxongir.userformapp.presenter.drafts_list

interface DraftDirection {
    suspend fun backToMain()
    suspend fun draftDetails(list: List<String>)
}