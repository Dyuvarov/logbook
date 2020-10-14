package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DefaultPathFilterTest {

    @Test
    public void testFilterOneReplacement() {
        final String path = "/profiles/123456789/user.json";
        final String expr = "/profiles/{organization}/user.json";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(result, is("/profiles/XXX/user.json"));
    }

    @Test
    public void testFilterTwoSideBySideReplacements() {
        final String path = "/profiles/123456789/test@test.com/user.json";
        final String expr = "/profiles/{organization}/{email}/user.json";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(result, is("/profiles/XXX/XXX/user.json"));
    }

    @Test
    public void testFilterTwoReplacements() {
        final String path = "/profiles/123456789/my/test@test.com/user.json";
        final String expr = "/profiles/{organization}/my/{email}/user.json";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(result, is("/profiles/XXX/my/XXX/user.json"));
    }

    @Test
    public void testFilterSubpath() {
        final String path = "/profiles/123456789/email/test@test.com/user/extra";
        final String expr = "/profiles/{organization}/email/{email}/user";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(result, is("/profiles/XXX/email/XXX/user/extra"));
    }

    @Test
    public void testFilterStart() {
        final String path = "/a/b/c/d/e";
        final String expr = "/{a}/b/c/d/e";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(result, is("/XXX/b/c/d/e"));
    }
    @Test
    public void testFilterStartNoSlash() {
        final String path = "a/b/c/d/e";
        final String expr = "{a}/b/c/d/e";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(result, is("XXX/b/c/d/e"));
    }

    @Test
    public void testFilterEnd() {
        final String path = "/a/b/c/d/e";
        final String expr = "/a/b/c/d/{e}";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(result, is("/a/b/c/d/XXX"));
    }

    @Test
    public void testReturnsSameInstance() {
        final String path = "/a/b/c/d/e";
        final String expr = "/a/b/{c}/d/e";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result1 = regexpPathUriFilter.filter(path);
        final String result2 = regexpPathUriFilter.filter(path);
        assertThat(result1, sameInstance(result2));
    }

    @Test
    public void testReturnsInputInstance() {
        final String path = "/b/c/d/e/f";
        final String expr = "/a/b/{c}/d/e";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(result, sameInstance(path));
    }

    @Test
    public void testIgnoresIncorrectExpression1() {
        final String path = "/a/b/c/d/e";
        final String expr = "/a/b/{c/d/e";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(path, sameInstance(result));
    }

    @Test
    public void testIgnoresIncorrectExpression2() {
        final String path = "/a/b/c/d/e";
        final String expr = "/a/b/c}/d/e";

        final PathFilter regexpPathUriFilter = new DefaultPathFilter("XXX", expr);

        final String result = regexpPathUriFilter.filter(path);
        assertThat(path, sameInstance(result));
    }

}
