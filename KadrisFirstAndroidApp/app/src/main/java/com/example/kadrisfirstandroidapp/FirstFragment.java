package com.example.kadrisfirstandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.kadrisfirstandroidapp.databinding.FragmentFirstBinding;
import com.glia.androidsdk.Engagement;
import com.glia.androidsdk.Glia;
import com.glia.androidsdk.queuing.QueueState;
import com.glia.androidsdk.queuing.Queue;
import com.glia.androidsdk.Engagement.MediaType;

import com.glia.widgets.GliaWidgets;
import com.glia.widgets.call.CallActivity;
import com.glia.widgets.chat.ChatActivity;

import java.util.Arrays;
import java.util.Optional;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.midButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }

        });


        // See if there are available queues

        Glia.getQueues((queueArray, exception) -> {
            if (exception != null) {
                // Something went wrong
                return;
            }
            Optional<Queue> selectedQueue = Arrays.stream(queueArray)
                    .filter(queue -> queue.getId().equals("116c746c-1a62-4883-a80b-01cfb2562c65"))
                    .filter(queue -> queue.getState().getStatus() == QueueState.Status.OPEN)
                    .filter(queue -> Arrays.asList(queue.getState().getMedias()).contains(Engagement.MediaType.VIDEO))
                    .findFirst();
            if (selectedQueue.isPresent()) {
                // Use selectedQueue.get().getId() to queue for engagement

                view.findViewById(R.id.left_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(requireContext(), CallActivity.class);
                        intent.putExtra(GliaWidgets.COMPANY_NAME, "DC Media");
                        intent.putExtra(GliaWidgets.QUEUE_ID, "116c746c-1a62-4883-a80b-01cfb2562c65");
                        intent.putExtra(GliaWidgets.CONTEXT_URL, "www.google.com");
                        intent.putExtra(GliaWidgets.MEDIA_TYPE, GliaWidgets.MEDIA_TYPE_VIDEO);
                        startActivity(intent);
                    }
                });

                view.findViewById(R.id.mid_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(requireContext(), ChatActivity.class);
                        intent.putExtra(GliaWidgets.COMPANY_NAME, "DC Media");
                        intent.putExtra(GliaWidgets.QUEUE_ID, "116c746c-1a62-4883-a80b-01cfb2562c65");
                        intent.putExtra(GliaWidgets.CONTEXT_URL, "www.google.com");
                        startActivity(intent);
                    }
                });

                view.findViewById(R.id.right_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(requireContext(), CallActivity.class);
                        intent.putExtra(GliaWidgets.COMPANY_NAME, "DC Media");
                        intent.putExtra(GliaWidgets.QUEUE_ID, "116c746c-1a62-4883-a80b-01cfb2562c65");
                        intent.putExtra(GliaWidgets.CONTEXT_URL, "www.google.com");
                        intent.putExtra(GliaWidgets.MEDIA_TYPE, GliaWidgets.MEDIA_TYPE_AUDIO);
                        startActivity(intent);
                    }
                });


            } else {
                getActivity().runOnUiThread(() -> {
                    // Notify visitor that all queues are closed at the moment
                    view.findViewById(R.id.right_button).setVisibility(View.GONE);
                });
            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}