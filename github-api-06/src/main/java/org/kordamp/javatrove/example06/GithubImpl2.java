package org.kordamp.javatrove.example06;

import org.kordamp.javatrove.example06.model.Repository;
import org.kordamp.javatrove.example06.service.Github;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Created by nikita on 05.09.17.
 */
public class GithubImpl2 implements Github {
    @Override
    public CompletableFuture<Collection<Repository>> repositories(String organization) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new IOException("gitHub is down!!!! ");
    }
}
