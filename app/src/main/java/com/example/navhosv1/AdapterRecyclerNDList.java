//package com.example.navhosv1;
//
//import android.service.autofill.Dataset;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.daimajia.swipe.SwipeLayout;
//import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Callback;
//public class AdapterRecyclerNDList extends RecyclerSwipeAdapter<AdapterRecyclerNDList.SimpleViewHolder> {
//
//    private Callback<List<Data>> mContext;
//    private ArrayList<Data> dataNextday;
//
//    public AdapterRecyclerNDList(Callback<List<Data>> context, ArrayList<Data> objects) {
//        this.mContext = context;
//        this.dataNextday = objects;
//    }
//
//
//    @Override
//    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nextdaylist_activity, parent, false);
//        return new SimpleViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
//
//        final Data item = dataNextday.get(position);
//
//        viewHolder.Name.setText(item.getNo() +"");
//        viewHolder.EmailId.setText("    "+item.getRoom());
//
//
// //       viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
//
//        //dari kiri
// //       viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));
////
////        //dari kanan
// //       viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));
////
////
////
////        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
////            @Override
////            public void onStartOpen(SwipeLayout layout) {
////
////            }
////
////            @Override
////            public void onOpen(SwipeLayout layout) {
////
////            }
////
////            @Override
////            public void onStartClose(SwipeLayout layout) {
////
////            }
////
////            @Override
////            public void onClose(SwipeLayout layout) {
////
////            }
////
////            @Override
////            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
////
////            }
////
////            @Override
////            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
////
////            }
////        });
//
////        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(mContext, " Click : " + item.getName() + " \n" + item.getEmailId(), Toast.LENGTH_SHORT).show();
////            }
////        });
//
////        viewHolder.btnLocation.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(v.getContext(), "Clicked on Information " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
////            }
////        });
//
////        viewHolder.Share.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                Toast.makeText(view.getContext(), "Clicked on Share " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
////            }
////        });
//
////        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
////                dataNextday.remove(position);
////                notifyItemRemoved(position);
////                notifyItemRangeChanged(position, dataNextday.size());
////                mItemManger.closeAllItems();
////                //    Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
////            }
////        });
//
////        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
////                dataNextday.remove(position);
////                notifyItemRemoved(position);
////                notifyItemRangeChanged(position, dataNextday.size());
////                mItemManger.closeAllItems();
////                Toast.makeText(v.getContext(), "Deleted " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
////            }
////        });
//
//  //      mItemManger.bindView(viewHolder.itemView, position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataNextday.size();
//    }
//
//    @Override
//    public int getSwipeLayoutResourceId(int position) {
//        return R.id.swipe;
//    }
//
//    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
//        public SwipeLayout swipeLayout;
//        public TextView Name;
//        public TextView EmailId;
//        public TextView Delete;
//        public TextView Edit;
//        public TextView Share;
//        public ImageButton btnLocation;
//        public SimpleViewHolder(View itemView) {
//            super(itemView);
//
//            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
//            Name = (TextView) itemView.findViewById(R.id.text_fn);
//            EmailId = (TextView) itemView.findViewById(R.id.text_ln);
////            Delete = (TextView) itemView.findViewById(R.id.Delete);
////            Edit = (TextView) itemView.findViewById(R.id.Edit);
////            Share = (TextView) itemView.findViewById(R.id.Share);
////            btnLocation = (ImageButton) itemView.findViewById(R.id.btnLocation);
//        }
//    }
//}
