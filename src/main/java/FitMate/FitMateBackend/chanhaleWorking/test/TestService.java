package FitMate.FitMateBackend.chanhaleWorking.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public TestWriteDomain getTestWriteDomain() {
        return testRepository.getTestWrite();
    }

    @Transactional
    public String writeTWD() {
        TestWriteDomain twd = testRepository.getTestWrite();
        String str = RandomStringUtils.randomAlphanumeric(15).toUpperCase();

        twd.setStr(str);
        return str;
    }
}
