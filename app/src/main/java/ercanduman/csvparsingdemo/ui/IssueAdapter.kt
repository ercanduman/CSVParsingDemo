package ercanduman.csvparsingdemo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ercanduman.csvparsingdemo.R
import ercanduman.csvparsingdemo.data.model.Issue
import ercanduman.csvparsingdemo.databinding.LayoutListItemIssueBinding

/**
 * Adapter is a class that adapts every item to a list viewHolder and responsible for providing
 * views that represent items in a data set.
 *
 * ListAdapter is base class of RecyclerView.Adapter for presenting List data in a RecyclerView,
 * including computing diffs between Lists on a background thread. While using a LiveData<List> is
 * an easy way to provide data to the adapter, it isn't required - you can use submitList(List) when
 * new lists are available.
 *
 * @author ercanduman
 * @since  11.07.2021
 */
class IssueAdapter : ListAdapter<Issue, IssueAdapter.IssueViewHolder>(ISSUE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding: LayoutListItemIssueBinding =
            LayoutListItemIssueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val currentIssue = getItem(position)
        if (currentIssue != null) {
            holder.bindIssue(currentIssue)
        }
    }

    class IssueViewHolder(private val binding: LayoutListItemIssueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindIssue(issue: Issue) {
            binding.apply {
                val context = root.context

                val dateOfBirth = context.getString(R.string.format_date_of_birth, issue.dateOfBirth)
                itemBirthDate.text = dateOfBirth

                val issueCount =
                    context.getString(R.string.format_issue_count, issue.issueCount.toString())
                itemIssueCount.text = issueCount

                val firstNameAndSurname = context.getString(
                    R.string.format_first_name_and_surname,
                    issue.firstname,
                    issue.surname
                )
                itemFirstNameAndSurname.text = firstNameAndSurname
            }
        }
    }

    companion object {
        private val ISSUE_COMPARATOR = object : DiffUtil.ItemCallback<Issue>() {
            override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean = oldItem == newItem
        }
    }
}