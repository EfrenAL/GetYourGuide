package com.getyourguide.efren.getyourguide.reviews.newReview

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.getyourguide.efren.getyourguide.R
import com.getyourguide.efren.getyourguide.model.Review
import com.getyourguide.efren.getyourguide.utils.random
import com.getyourguide.efren.getyourguide.utils.transformToGygDate
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_new_review.*
import java.util.*
import javax.inject.Inject


class NewReviewFragment : Fragment() {

    interface NavigateTo{
        fun navigateToReviewsFragment()
    }

    @Inject
    lateinit var newReviewViewModelFactory: NewReviewViewModelFactory
    private lateinit var viewModel: NewReviewViewModel

    private var callback: NavigateTo? = null
    private var errorSnackbar: Snackbar? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)
        //configureViewModel
        viewModel = ViewModelProviders.of(this, newReviewViewModelFactory).get(NewReviewViewModel::class.java)
        viewModel.success.observe(this, android.arch.lifecycle.Observer {
            if (it == true)
                callback!!.navigateToReviewsFragment()
            else
                displayError(R.string.error_insert_review)
        })
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_review, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_new_review, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            R.id.action_check -> submitReview()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = activity as NavigateTo
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement NewReviewFragment.NavigateTo")
        }
    }

    private fun submitReview() {
        hideKeyboard()
        viewModel.insertNewReview(Review((0..10000000).random(),
                rb_edit_rating.rating.toDouble(),
                et_title.text.toString(),
                et_message.text.toString(),
                "Pepito Grillo - Disneyland",//ToDo mock data author review
                false,               //ToDo mock data
                Date().transformToGygDate(),
                null,                  //ToDo mock data
                "es",
                "solo",                 //ToDo mock data
                "Pepito Grillo",       //ToDo mock data
                "Disneyland"         //ToDo mock data
        ))
    }

    private fun displayError(error: Int?) {
        if (error != null) {
            errorSnackbar = Snackbar.make(ll_new_review, error, Snackbar.LENGTH_SHORT)
            errorSnackbar?.show()
        } else {
            errorSnackbar?.dismiss()
        }
    }

    private fun hideKeyboard() {
        val imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }
}