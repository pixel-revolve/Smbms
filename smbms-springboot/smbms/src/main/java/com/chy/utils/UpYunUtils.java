package com.chy.utils;

import com.upyun.RestManager;
import com.upyun.UpException;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UpYunUtils {

    private static final String BUCKET_NAME = "pixel-revolve";

    private static final String OPERATOR_NAME = "pixelrevolve";

    private static final String OPERATOR_PWD = "HtLlUj9mCVPGzy0nekuEU7AGaCX6EFsL";

    private static final String ROOT_PATH = "/images/";

    private RestManager restManager = new RestManager(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);

    public boolean uploadImg(String fileName, byte[] bytes) throws IOException, UpException {

        String lastPath = ROOT_PATH + fileName;

        Response response = restManager.writeFile(lastPath, bytes, null);
        return response.isSuccessful();

    }


}
