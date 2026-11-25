package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/** Re-runs a failed test up to MAX_RETRY times before TestNG reports it as failed. */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRY = 2;
    private static final Logger LOG = LogManager.getLogger(RetryAnalyzer.class);

    // keyed per test + data-provider row so each row gets its own 2 retries (and parallel threads don't clash)
    private static final Map<String, AtomicInteger> ATTEMPTS = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        String key = result.getTestClass().getName() + "." + result.getMethod().getMethodName()
                + Arrays.toString(result.getParameters());
        int used = ATTEMPTS.computeIfAbsent(key, k -> new AtomicInteger()).incrementAndGet();
        if (used <= MAX_RETRY) {
            LOG.warn("Retrying '{}' (retry {} of {})", result.getName(), used, MAX_RETRY);
            return true;
        }
        return false;
    }
}
