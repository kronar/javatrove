package org.kordamp.javatrove.example06;

import org.kordamp.javatrove.example06.model.Repository;
import org.kordamp.javatrove.example06.service.Github;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Copy code below with imports section into FIT file githubFIT.impl
 */
public class GithubImpl2 implements Github {
    @Override
    public CompletableFuture<Collection<Repository>> repositories(String organization) {
        try {
            throw new RuntimeException("GitHub is down");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Github is down");
    }
}
