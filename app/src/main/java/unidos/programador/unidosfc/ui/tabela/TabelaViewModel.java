package unidos.programador.unidosfc.ui.tabela;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TabelaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TabelaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}