package darkoverload.itzip.feature.resume.util.holder;


import java.util.UUID;

public class UUIDHolderImpl implements UUIDHolder{

    @Override
    public String UUIDGenerate() {
        return UUID.randomUUID().toString();
    }

}
