package com.example.sid.marwadishaadi.Dashboard_Interest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sid.marwadishaadi.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InterestReceivedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InterestReceivedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InterestReceivedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = "InterestActivity";
    private Context mContext;
    private List<InterestModel> intererstList;
    private RecyclerView recyclerView;
    private InterestAdapter interestAdapter;
    private View view;
    private CoordinatorLayout coordinatorLayout;
    private Paint p = new Paint();
    private OnFragmentInteractionListener mListener;

    public InterestReceivedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InterestReceivedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InterestReceivedFragment newInstance(String param1, String param2) {
        InterestReceivedFragment fragment = new InterestReceivedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_interest_received, container, false);

        coordinatorLayout = (CoordinatorLayout) mview.findViewById(R.id.interest_layout);
        recyclerView = (RecyclerView) mview.findViewById(R.id.card_recycler_view);
        intererstList = new ArrayList<>();
        interestAdapter = new InterestAdapter(getContext(), intererstList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(interestAdapter);
        prepareInterest();

        ItemTouchHelper.SimpleCallback touchevents = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                InterestModel positem = intererstList.get(position);
                if(direction == ItemTouchHelper.RIGHT){
                    // accept state
                    final InterestModel temp = intererstList.get(position);
                    intererstList.remove(position);
                    interestAdapter.notifyItemRemoved(position);
                    interestAdapter.notifyItemRangeChanged(position,intererstList.size());
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout,"Interest Accepted ! ",Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intererstList.add(temp);
                                    interestAdapter.notifyDataSetChanged();
                                    Snackbar sl = Snackbar.make(coordinatorLayout,"Interest ",Snackbar.LENGTH_LONG);
                                    sl.show();
                                }
                            });
                    snackbar.show();
                }else{
                    // reject state
                    final InterestModel tmp = intererstList.get(position);
                    InterestModel interestModel = intererstList.get(position);
                    interestModel.setStatus(1);
                    interestAdapter.notifyDataSetChanged();
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout,"Interest Rejected ! ",Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    intererstList.add(tmp);
                                    interestAdapter.notifyDataSetChanged();
                                    Snackbar sl = Snackbar.make(coordinatorLayout,"Interest ",Snackbar.LENGTH_LONG);
                                    sl.show();
                                }
                            });
                    snackbar.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.accepted);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.rejected);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchevents);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return mview;
    }

    private void prepareInterest() {


        InterestModel interestModelF = new InterestModel("Tyrion,", "18", "Former Hand of Kind", "Seven Kingdom","https://avatars2.githubusercontent.com/u/13920107?v=3&s=460",0);
        intererstList.add(interestModelF);

        InterestModel interestModelS = new InterestModel("Cersie,", "18", "Former Hand of Kind", "Seven Kingdom", "https://scontent.fbom1-1.fna.fbcdn.net/v/t1.0-9/542932_1870229913255094_1073152164360738877_n.jpg?oh=414cb9683ac4daa8f7a67f63316215f6&oe=59ABFA15",1);
        intererstList.add(interestModelS);

        InterestModel interestModelT = new InterestModel("Jon,", "18", "Former Hand of Kind", "Seven Kingdom","https://lh3.googleusercontent.com/-_2FnlCFcgfA/WTUS3yDAXII/AAAAAAAAIHg/IRXv4NgTIcMlyoDaGfr8IYdVR2y_9ccugCK8B/s512/2017-06-05.jpg",2);
        intererstList.add(interestModelT);

        Log.d(TAG, "prepareInterest: here called interest .........");

        interestAdapter.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
