package dev.dgomes.footballNews.data.remote;

public record NewsRemoteEntity(
        int id,
        String title,
        String text,
        String image,
        String url
) {
}