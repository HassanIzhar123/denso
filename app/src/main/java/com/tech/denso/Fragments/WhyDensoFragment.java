package com.tech.denso.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tech.denso.Activities.DashboardActivity;
import com.tech.denso.R;

public class WhyDensoFragment extends Fragment {
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_whydenso, container, false);
        DashboardActivity.titletextview.setText("Why Denso");
        return v;
    }
}
//public class WhyDensoFragment extends Fragment {
//    View v;
//    RecyclerView servicingrecyclerview;
//    RelativeLayout loadingrel;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        v = inflater.inflate(R.layout.fragment_whydenso, container, false);
//        servicingrecyclerview = v.findViewById(R.id.servicingrecyclerview);
//        loadingrel = v.findViewById(R.id.loadingrel);
//        DisplayPackagesData();
//        return v;
//    }
//
//    private void DisplayPackagesData() {
//        new TaskRunner().executeAsync(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                ArrayList<String>servicename=new ArrayList<>();
//                ArrayList<String>serviceheader=new ArrayList<>();
//                ArrayList<String>servicedescription=new ArrayList<>();
//                servicename.add("Servicing");
//                servicename.add("Engine Service");
//                servicename.add("Suspension & Drivetrain");
//                servicename.add("Electrical & Electronics");
//                serviceheader.add("Quality maintenance to extend the life of your vehicle");
//                serviceheader.add("Engine reapir & replacement with performance check");
//                serviceheader.add("Precision alignment for smooth & stable handling");
//                serviceheader.add("Complete electrical system check-up & repair");
//                servicedescription.add("We offer periodic vehicle maintenance using Demo ports to ensure miles of hassle-free performance All repairs and parts are Offer. MP a Warranty.");
//                servicedescription.add("Our experlenced team utilizes professional car diagnostic tools to detect any issue, and conduct repairs on any make ancl malel of vehicles. ");
//                servicedescription.add("Do you have an issue with your steering or suspension? " +
//                        "Drive-in tor a quick alignment check. We will ensure " +
//                        "precision alignment so that you can enjoy a safe and " +
//                        "smooth ride as well as benefit from long-lasting tires and " +
//                        "improved fuel efficiency");
//                servicedescription.add("Complete electrical system check-up & repair " +
//                        "From alternators to spark plugs, we can correct any faults " +
//                        "or variances to restore full power to your vehicle, using industry -standard electrical system diagnostics");
//                if (servicename.size() > 0) {
//                    WhyDensoServicesAdapter adapter = new WhyDensoServicesAdapter(getContext(),servicename,serviceheader,servicedescription);
//                    servicingrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//                    servicingrecyclerview.setAdapter(adapter);
//                    loadingrel.setVisibility(View.GONE);
//                    servicingrecyclerview.setVisibility(View.VISIBLE);
//                } else {
//                    loadingrel.setVisibility(View.GONE);
//                    servicingrecyclerview.setVisibility(View.GONE);
//                }
//                return null;
//            }
//        }, new TaskRunner.Callback<Object>() {
//            @Override
//            public void onStart() {
//                loadingrel.setVisibility(View.VISIBLE);
//                servicingrecyclerview.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onComplete(Object result) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadingrel.setVisibility(View.GONE);
//                        servicingrecyclerview.setVisibility(View.VISIBLE);
//                    }
//                }, 3000);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.e("Eceptionindialog", "" + e.toString());
//                loadingrel.setVisibility(View.GONE);
//                servicingrecyclerview.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//}
