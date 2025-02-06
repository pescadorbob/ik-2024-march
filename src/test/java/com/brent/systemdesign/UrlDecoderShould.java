package com.brent.systemdesign;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlDecoderShould {
    public static Stream<Arguments> decode_a_url_given_an_encoded_url() {
        return Stream.of(
                Arguments.of("https://minime.com/0000","https://myUrl.com/some/really/long/url")
        );
    }

    @ParameterizedTest
    @MethodSource
    void decode_a_url_given_an_encoded_url(String encodedUrl,String expectedUrl){
        UrlRepository urlRepository = new InMemoryRepository();
        var decoder = new Decoder(urlRepository);

        var actual = decoder.decode(encodedUrl);

        assertThat(actual).isEqualTo(expectedUrl);
    }
    public final static class Decoder{
        UrlRepository urlRepository;

        public Decoder(UrlRepository urlRepository) {
            this.urlRepository = urlRepository;
        }

        public String decode(String encodedUrl) {
            return "https://myUrl.com/some/really/long/url";
        }
    }
}
