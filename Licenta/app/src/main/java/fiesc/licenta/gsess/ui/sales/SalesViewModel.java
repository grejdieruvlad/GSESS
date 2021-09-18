package fiesc.licenta.gsess.ui.sales;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SalesViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    public SalesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is sales fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}