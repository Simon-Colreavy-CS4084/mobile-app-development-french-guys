package ie.ul.makevent.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ie.ul.makevent.databinding.ItemContainerRecentConversionBinding;
import ie.ul.makevent.listeners.ConversionListener;
import ie.ul.makevent.models.ChatMessage;
import ie.ul.makevent.models.User;
import ie.ul.makevent.utilities.Constants;
import ie.ul.makevent.utilities.PreferenceManager;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder> {

    private final List<ChatMessage> chatMessages;
    private final ConversionListener conversionListener;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener)
    {
        this.chatMessages = chatMessages;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                ItemContainerRecentConversionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder{

        ItemContainerRecentConversionBinding binding;
        private PreferenceManager preferenceManager;


        ConversionViewHolder(ItemContainerRecentConversionBinding itemContainerRecentConversionBinding)
        {
            super(itemContainerRecentConversionBinding.getRoot());
            binding = itemContainerRecentConversionBinding;
            preferenceManager = new PreferenceManager(binding.getRoot().getContext());


        }

        void setData(ChatMessage chatMessage)
        {
            binding.imageProfile.setImageBitmap(getConversionImage(chatMessage.conversionImage));
            binding.textName.setText(chatMessage.conversionName);
            String lastMessage;
            if (chatMessage.senderId.equals(preferenceManager.getString(Constants.KEY_USER_ID)))
            {
                lastMessage = "You";
            }
            else
            {
                lastMessage = chatMessage.conversionName;
            }
            //Log.e("setData", chatMessage.message + " - sender : " + chatMessage.senderId + " receiver : " + chatMessage.receiverId + "\nuser : "  + preferenceManager.getString(Constants.KEY_USER_ID));
            lastMessage += " : " + chatMessage.message;
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v -> {
                User user = new User();
                user.id = chatMessage.conversionId;
                user.name = chatMessage.conversionName;
                user.image = chatMessage.conversionImage;
                conversionListener.onConversionClicked(user);
            });
        }
    }

    private Bitmap getConversionImage(String encodedImage)
    {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
