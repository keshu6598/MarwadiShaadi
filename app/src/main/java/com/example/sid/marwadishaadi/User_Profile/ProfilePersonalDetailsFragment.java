package com.example.sid.marwadishaadi.User_Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.sid.marwadishaadi.Similar_Profiles.SimilarActivity;
import com.example.sid.marwadishaadi.User_Profile.Edit_User_Profile.EditPersonalDetailsActivity;
import com.example.sid.marwadishaadi.R;
import com.example.sid.marwadishaadi.Search.BottomSheet;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;


public class ProfilePersonalDetailsFragment extends Fragment {

    private TextView edit_individual;
    private TextView edit_education;
    private TextView edit_profession;
    private static int casebreak;
    private Button similar;
    private OnFragmentInteractionListener mListener;

    TextView name_age,maritalStatus, birthdate, gender, address, mobileNo, caste, height, weight, complexion_build, physicalStatus, education, educationDegree, collegeName_collegeLocation, currentOccupation, designation_companyName, companyLocation, annualIncome;


    public ProfilePersonalDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview =  inflater.inflate(R.layout.fragment_profile__personal__details, container, false);

        edit_individual = (TextView) mview.findViewById(R.id.individual_clear);
        edit_education = (TextView) mview.findViewById(R.id.edu_clear);
        edit_profession = (TextView) mview.findViewById(R.id.profession_clear);
        similar = (Button) mview.findViewById(R.id.similar);

        name_age=(TextView) mview.findViewById(R.id.name_age);
        maritalStatus=(TextView) mview.findViewById(R.id.marital_status);
        birthdate=(TextView) mview.findViewById(R.id.birthdate);
        gender=(TextView) mview.findViewById(R.id.gender);
        address=(TextView) mview.findViewById(R.id.address);
        mobileNo=(TextView) mview.findViewById(R.id.contact_mob);
        caste=(TextView) mview.findViewById(R.id.caste);
        height=(TextView) mview.findViewById(R.id.height);
        weight=(TextView) mview.findViewById(R.id.weight);
        complexion_build=(TextView) mview.findViewById(R.id.complexion_build);
        physicalStatus=(TextView) mview.findViewById(R.id.physical_status);
        education=(TextView) mview.findViewById(R.id.degree);
        educationDegree=(TextView) mview.findViewById(R.id.edu_degree);
        collegeName_collegeLocation=(TextView) mview.findViewById(R.id.collegeName_collegeLocation);
        currentOccupation=(TextView) mview.findViewById(R.id.current_occup);
        designation_companyName=(TextView) mview.findViewById(R.id.occupDesignation_occupCompany);
        companyLocation=(TextView) mview.findViewById(R.id.occup_location);
        annualIncome=(TextView) mview.findViewById(R.id.annual_income);







        new PersonalDetails().execute();

        similar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SimilarActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        edit_individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),EditPersonalDetailsActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        edit_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casebreak=12;
                BottomSheetDialogFragment btm= new BottomSheet(1);
                btm.show(getFragmentManager(),btm.getTag());
            }
        });


        edit_profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                casebreak=13;
                BottomSheetDialogFragment btm= new BottomSheet(1);
                btm.show(getFragmentManager(),btm.getTag());
            }
        });

        return mview;
    }

    class PersonalDetails extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            AndroidNetworking.post("http://192.168.43.143:5050/profilePersonalDetails")
                    .addBodyParameter("customerNo","A1028")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, "onResponse: ******************in personal details");
                            try {
                                JSONArray array = response.getJSONArray(0);

                                String dateOfBirth = array.getString(2);
                                // Thu, 18 Jan 1990 00:00:00 GMT
                                DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
                                Date date = null;
                                try {
                                    date = formatter.parse(dateOfBirth);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(date);
                                String[] str = {"January",
                                        "February",
                                        "March",
                                        "April",
                                        "May",
                                        "June",
                                        "July",
                                        "August",
                                        "September",
                                        "October",
                                        "November",
                                        "December"};
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(date);
                                String formatedDate = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
                                String strDate = cal.get(Calendar.DATE) + " " + str[(cal.get(Calendar.MONTH) )] + " " + cal.get(Calendar.YEAR);

                                String[] partsOfDate = formatedDate.split("-");
                                int day = Integer.parseInt(partsOfDate[0]);
                                int month = Integer.parseInt(partsOfDate[1]);
                                int year = Integer.parseInt(partsOfDate[2]);
                                int a = getAge(year, month, day);

                                String na=array.getString(0)+ " "+array.getString(1)+", "+ a + " yrs";
                                name_age.setText(na);
                                maritalStatus.setText(array.getString(3));
                                birthdate.setText(strDate);
                                gender.setText(array.getString(4));
                                address.setText(array.getString(5));
                                mobileNo.setText(array.getString(6));

                                String[] c=array.getString(7).split("");
                                String cast="";
                                if(c[1].equals("A")){
                                    cast="Agarwal";
                                }
                                else if(c[1].equals("K")){
                                    cast="Khandelwal";
                                }
                                else if(c[1].equals("J")) {
                                    cast="Jain";
                                }
                                else if(c[1].equals("M")){
                                    cast="Maheshwari";
                                }
                                else if(c[1].equals("O")){
                                    cast="Other";
                                }



                                caste.setText(cast);

                                double feet = Double.parseDouble(array.getString(9)) / 30.48;
                                double inches = (Double.parseDouble(array.getString(9)) / 2.54) - ((int) feet * 12);
                                String h = "Stands tall at "+(int) feet + "'" + (int) inches;
                                height.setText(h);
                                String w = "weighs "+array.getString(10)+" kgs";
                                weight.setText(w);

                                String cb=array.getString(11)+", "+array.getString(12);

                                complexion_build.setText(cb);
                                physicalStatus.setText(array.getString(13));
                                education.setText(array.getString(14));
                                educationDegree.setText(array.getString(15));

                                String cnl=array.getString(16)+", "+array.getString(17);
                                collegeName_collegeLocation.setText(cnl);
                                currentOccupation.setText(array.getString(18));

                                String dc=array.getString(21)+" at "+array.getString(20);
                                designation_companyName.setText(dc);

                                String cl="Located in " + array.getString(22);
                                companyLocation.setText(cl);





                                String annualI = array.getString(19);
                                annualI = annualI.replaceAll("[^-?0-9]+", " ");
                                List<String> incomeArray = Arrays.asList(annualI.trim().split(" "));
                                Log.d(TAG, "onResponse: income array is " + incomeArray);
                                if (annualI.contains("Upto")) {
                                    annualI = "Upto 3L";
                                } else if (annualI.contains("Above")) {
                                    annualI = "Above 50L";

                                } else if (incomeArray.size() == 3) {
                                    Log.d(TAG, "onResponse: when three");
                                    double first = Integer.parseInt(incomeArray.get(0)) / 100000.0;
                                    double second = Integer.parseInt(incomeArray.get(2)) / 100000.0;
                                    annualI = (int) first + "L - " + (int) second + "L";
                                } else {
                                    annualI= "No Income mentioned.";
                                }

                                annualIncome.setText(annualI );






                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError error) {

                        }
                    });

            return null;
        }
    }


    public int getAge(int DOByear, int DOBmonth, int DOBday) {

        int age;

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if (DOBmonth > currentMonth) {
            --age;
        } else if (DOBmonth == currentMonth) {
            if (DOBday > todayDay) {
                --age;
            }
        }
        return age;
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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
    public int getCasebreak()
    {
        return this.casebreak;
    }
}
