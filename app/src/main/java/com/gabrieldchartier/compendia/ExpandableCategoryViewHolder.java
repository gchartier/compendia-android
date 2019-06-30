package com.gabrieldchartier.compendia;

import android.view.View;
import android.widget.TextView;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class ExpandableCategoryViewHolder extends GroupViewHolder
{

        private TextView category;

        public ExpandableCategoryViewHolder(View itemView)
        {
            super(itemView);
            category = itemView.findViewById(R.id.categoryListItem);
        }

        public void setCategoryTitle(ExpandableGroup group)
        {
            category.setText(group.getTitle());
        }
}
