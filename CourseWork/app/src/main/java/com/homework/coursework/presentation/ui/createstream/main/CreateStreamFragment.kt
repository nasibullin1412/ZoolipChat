package com.homework.coursework.presentation.ui.createstream.main

import android.os.Bundle
import android.view.View
import com.homework.coursework.domain.entity.SubscribeData
import com.homework.coursework.presentation.App
import com.homework.coursework.presentation.ui.createstream.BaseCreateStreamFragment
import com.homework.coursework.presentation.ui.createstream.elm.Effect
import com.homework.coursework.presentation.ui.createstream.elm.Event
import com.homework.coursework.presentation.ui.createstream.elm.State
import com.homework.coursework.presentation.utils.showToast
import vivid.money.elmslie.core.store.Store
import javax.inject.Inject

class CreateStreamFragment : BaseCreateStreamFragment() {

    private lateinit var subscribeData: SubscribeData

    @Inject
    internal lateinit var subscribeStreamStore: Store<Event, Effect, State>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.createNewStreamComponent().inject(this)
    }

    override fun initBackButton() {
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun initErrorRepeat() {
        binding.errorContent.tvRepeat.setOnClickListener {
            store.accept(Event.Ui.SubscribeToStream(subscribeData))
        }
    }

    override fun initCreateStreamButton() {
        binding.btnCreate.setOnClickListener {
            subscribeData = getDataFromFields()
            store.accept(Event.Ui.SubscribeToStream(subscribeData))
        }
    }

    override val initEvent: Event
        get() = Event.Ui.SubscribeToStream(SubscribeData.Builder().build())

    override fun createStore(): Store<Event, Effect, State> = subscribeStreamStore

    /**
     * show error layout
     */
    private fun showErrorScreen() {
        with(binding) {
            svCreateNewStream.visibility = View.GONE
            nsvErrorConnection.visibility = View.VISIBLE
        }
    }

    override fun render(state: State) {
        if (state.isError) {
            showToast(state.error?.message)
        }
        if (state.item is Boolean) {
            if (!state.item) {
                showErrorScreen()
            }
        }
    }

    override fun handleEffect(effect: Effect) = when (effect) {
        is Effect.ErrorSubscribe -> {
            showToast(effect.error.message)
        }
        is Effect.SuccessSubscribe -> {
            showToast("$SUCCESS_SUBSCRIBE ${effect.data}")
        }
    }

    private fun getDataFromFields(): SubscribeData {
        return binding.run {
            SubscribeData.Builder()
                .name(etStreamName.text.toString())
                .description(etStreamDescription.text.toString())
                .isWebPublic(radioPublic.isChecked)
                .inviteOnly(radioPrivateProtected.isChecked)
                .historyPublicToSubscribers(radioPrivateShared.isChecked)
                .build()
        }
    }

    companion object {
        const val SUCCESS_SUBSCRIBE = "Subscribe result: "
    }
}
