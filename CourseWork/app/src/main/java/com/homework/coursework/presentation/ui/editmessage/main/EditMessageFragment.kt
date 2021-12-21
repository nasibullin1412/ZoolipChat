package com.homework.coursework.presentation.ui.editmessage.main

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.adapter.data.chat.MessageItem
import com.homework.coursework.presentation.ui.editmessage.BaseEditMessage
import com.homework.coursework.presentation.ui.editmessage.elm.Effect
import com.homework.coursework.presentation.ui.editmessage.elm.Event
import com.homework.coursework.presentation.ui.editmessage.elm.State
import com.homework.coursework.presentation.utils.showToast
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class EditMessageFragment : BaseEditMessage() {

    private lateinit var currMessageItem: MessageItem

    @Inject
    internal lateinit var editMessageStore: Store<Event, Effect, State>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.editMessageComponent().inject(this)
    }

    override fun initArguments() {
        currMessageItem = requireArguments().getParcelable(MESSAGE_KEY)
            ?: throw IllegalArgumentException("Message required")
    }

    override fun initBackButton() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun initEditMessageButton() {
        with(binding) {
            setFragmentResult(EDIT_MESSAGE_REQUEST_KEY, Bundle())
            btnChange.setOnClickListener {
                currMessageItem.topicName = etTopicName.text.toString()
                currMessageItem.messageContent = etMessageContent.text.toString()
                store.accept(Event.Ui.EditMessage(currMessageItem))
            }
        }
    }

    override val initEvent: Event
        get() = Event.Ui.InitView(currMessageItem)

    override fun createStore(): Store<Event, Effect, State> = editMessageStore

    override fun render(state: State) {
        if (state.isUpdate && state.item is MessageItem) {
            with(binding) {
                etTopicName.setText(state.item.topicName)
                etMessageContent.setText(state.item.messageContent)
            }
        }
    }

    override fun handleEffect(effect: Effect) = when (effect) {
        is Effect.ErrorEdit -> showToast(effect.error.message)
        Effect.SuccessEdit -> showToast(SUCCESS_EDIT)
    }

    companion object {
        const val SUCCESS_EDIT = "Success edit"
        const val EDIT_MESSAGE_REQUEST_KEY = "editRequestKey"
        private const val MESSAGE_KEY = "MESSAGE_KEY"

        fun createBundle(messageItem: MessageItem): Bundle {
            return Bundle().apply { putParcelable(MESSAGE_KEY, messageItem) }
        }

        fun newInstance(args: Bundle): EditMessageFragment {
            val fragment = EditMessageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
