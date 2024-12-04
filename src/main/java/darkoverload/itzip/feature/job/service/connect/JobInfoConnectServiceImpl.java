package darkoverload.itzip.feature.job.service.connect;

import darkoverload.itzip.feature.job.domain.ConnectJobInfo;
import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.repository.JobInfoRepository;
import darkoverload.itzip.feature.job.repository.JobInfoScrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobInfoConnectServiceImpl implements JobInfoConnectService {

    private final JobInfoRepository jobInfoRepository;
    private final JobInfoScrapRepository jobInfoScrapRepository;

    @Value("${job.api-url}")
    private String jobUrl;

    @Value("${job.api-key}")
    private String jobKey;

    /**
     * API와 연결하여 모든 JobInfo 데이터를 가져옵니다.
     * 데이터를 페이지 단위로 나누어 가져와서 리스트에 추가한 후, 전체 데이터를 반환합니다.
     *
     * @return API로부터 가져온 모든 JobInfo 데이터의 리스트
     */
    @Override
    public List<JobInfo> jobInfoConnect() {
        // API에서 전체 JobInfo 데이터의 개수를 가져옴
        int totalCount = ConnectJobInfo.getTotalCount(jobUrl, jobKey);

        // 총 아이템 수를 기준으로 필요한 페이지 수를 계산
        int pages = calculatePageCount(totalCount);

        // API로부터 가져온 모든 JobInfo 데이터를 저장할 리스트
        List<JobInfo> apiDataList = new ArrayList<>();

        // 각 페이지의 데이터를 가져와 리스트에 추가
        for(int i=0;i<pages; i++){
            apiDataList.addAll(ConnectJobInfo.getJobInfoData(jobUrl, jobKey, i));
        }

        // 모든 페이지의 데이터를 가져온 후 반환
        return apiDataList;
    }

    /**
     * API에서 가져온 JobInfo 데이터와 데이터베이스에 저장된 JobInfo 데이터를 비교하여,
     * 데이터베이스에만 존재하는 데이터를 삭제하고, 삭제된 데이터의 수를 반환합니다.
     *
     * @param apiDataList API로부터 가져온 최신 JobInfo 데이터 목록
     * @param dbList 데이터베이스에서 조회한 기존 JobInfo 데이터 목록
     * @return 삭제된 JobInfo 항목의 총 개수
     */
    @Override
    public Long jobInfoDelete(List<JobInfo> apiDataList, List<JobInfo> dbList) {
        // dbList가 비어있을 경우, 즉시 반환하여 추가 작업을 방지
        if(dbList.isEmpty()) return 0L;

        List<Long> deleteList = makeDeleteList(apiDataList, dbList);


        long totalDeletedCount = 0L;
        // batchSize를 설정하여 500개씩 나누어 삭제 작업을 수행 (대량 삭제 시 성능 최적화)
       for(int i=0; i < deleteList.size(); i+= 500){
           List<Long> batch = deleteList.subList(i, Math.min(i + 500, deleteList.size()));
           jobInfoScrapRepository.bulkDeleteByPositionIds(batch);
           totalDeletedCount += jobInfoRepository.bulkDeleteByPositionIds(batch);
       }

       // 최종적으로 삭제된 레코드의 총 개수를 반환
       return totalDeletedCount;
    }


    /**
     * API에서 가져온 최신 JobInfo 데이터와 데이터베이스에 저장된 기존 데이터를 비교하여,
     * 변경된 JobInfo 객체들을 찾아 업데이트를 수행합니다. 업데이트된 레코드의 총 개수를 반환합니다.
     *
     * @param apiDataList API로부터 가져온 최신 JobInfo 데이터 목록
     * @param dbList 데이터베이스에서 조회한 기존 JobInfo 데이터 목록
     * @return 업데이트된 레코드의 총 개수
     */
    @Override
    public Long jobInfoUpdate(List<JobInfo> apiDataList, List<JobInfo> dbList) {
        // 변경된 JobInfo 객체들을 찾기 위한 리스트 생성
        List<JobInfo> updateList = makeUpdateList(apiDataList, dbList);

        // 업데이트된 레코드의 총 개수를 저장할 변수
        long totalUpdatedCount = 0L;

        // 업데이트 작업을 배치 단위로 나누어 처리 (500개씩)
        for(int i=0; i< updateList.size(); i+=500){

            // 500개씩 잘라낸 부분 리스트를 배치로 처리
            List<JobInfo> batch = updateList.subList(i, Math.min(i + 500, updateList.size()));

            // 현재 배치를 데이터베이스에 저장하고, 저장된 레코드 수를 총 개수에 누적
            totalUpdatedCount += jobInfoRepository.saveAll(batch)
                                                  .size();
        }

        // 최종적으로 업데이트된 레코드의 총 개수를 반환
        return totalUpdatedCount;
    }



    /**
     * 주어진 API 데이터 목록과 데이터베이스 목록을 비교하여, 데이터베이스에 없는 새로운 JobInfo 객체들을
     * 저장하고, 저장된 레코드의 총 개수를 반환합니다.
     *
     * @param apiDataList API로부터 가져온 최신 JobInfo 데이터 목록
     * @param dbList 데이터베이스에서 조회한 기존 JobInfo 데이터 목록
     * @return 저장된 레코드의 총 개수
     */
    @Override
    public Long jobInfoSave(List<JobInfo> apiDataList, List<JobInfo> dbList) {
        List<JobInfo> insertList = makeSaveList(apiDataList, dbList);

        long totalSaveCount = 0L;

        // 배치 단위로 500개씩 나누어 저장 작업을 수행
        for(int i=0; i < insertList.size() ; i+=500) {
            List<JobInfo> batch =insertList.subList(i, Math.min(i + 500, insertList.size()));

            totalSaveCount += jobInfoRepository.saveAll(batch)
                                               .size();
        }

        // 배치 단위로 500개씩 나누어 저장 작업을 수행
        return totalSaveCount;
    }

    /**
     * 주어진 총 아이템 수(totalCount)를 기준으로 페이지 수를 계산합니다.
     * 한 페이지에 110개의 아이템이 포함된다고 가정합니다.
     *
     * @param totalCount 전체 아이템 수
     * @return 필요한 총 페이지 수
     */
    private int calculatePageCount(int totalCount) {

        // 전체 아이템 수를 110으로 나누어 페이지 수를 계산하고, 나머지가 있으면 추가 페이지를 고려
        int pages = totalCount / 110 + 1;

        return pages;
    }


    /**
     * 두 JobInfo 객체의 필드를 비교하여, 모든 필드가 다른 경우 true를 반환합니다.
     * 만약 하나라도 같은 필드가 있으면 false를 반환합니다.
     *
     * @param dbJobInfo 데이터베이스에서 조회한 JobInfo 객체
     * @param apiJobInfo API로부터 가져온 JobInfo 객체
     * @return 모든 필드가 서로 다른 경우 true, 하나라도 같은 필드가 있는 경우 false
     */
    private boolean checkNotEquals(JobInfo dbJobInfo, JobInfo apiJobInfo) {

        return !dbJobInfo.getActive().equals(apiJobInfo.getActive()) // active 필드 비교
                || !dbJobInfo.getUrl().equals(apiJobInfo.getUrl()) // URL 필드 비교
                || !dbJobInfo.getTitle().equals(apiJobInfo.getTitle()) // 제목 필드 비교
                || !dbJobInfo.getIndustryCode().equals(apiJobInfo.getIndustryCode()) // 산업 코드 비교
                || !dbJobInfo.getIndustryName().equals(apiJobInfo.getIndustryName()) // 산업 이름 비교
                || !dbJobInfo.getLocationName().equals(apiJobInfo.getLocationName()) // 위치 이름 비교
                || !dbJobInfo.getJobMidCode().equals(apiJobInfo.getJobMidCode()) // 중간 직무 코드 비교
                || !dbJobInfo.getJobMidName().equals(apiJobInfo.getJobMidName()) // 중간 직무 이름 비교
                || !dbJobInfo.getJobName().equals(apiJobInfo.getJobName()) // 직무 이름 비교
                || !dbJobInfo.getJobCode().equals(apiJobInfo.getJobCode()) // 직무 코드 비교
                || !dbJobInfo.getExperienceCode().equals(apiJobInfo.getExperienceCode()) // 경력 코드 비교
                || !dbJobInfo.getExperienceName().equals(apiJobInfo.getExperienceName()) // 경력 이름 비교
                || !dbJobInfo.getSalaryName().equals(apiJobInfo.getSalaryName()) // 급여 이름 비교
                || !dbJobInfo.getPostingDate().equals(apiJobInfo.getPostingDate()) // 게시 날짜 비교
                || !dbJobInfo.getExpirationDate().equals(apiJobInfo.getExpirationDate()) // 만료 날짜 비교
                || !dbJobInfo.getCloseTypeCode().equals(apiJobInfo.getCloseTypeCode()) // 마감 유형 코드 비교
                || !dbJobInfo.getCloseTypeName().equals(apiJobInfo.getCloseTypeName()); // 마감 유형 이름 비교

    }

    /**
     * API로부터 가져온 데이터와 데이터베이스에 저장된 데이터를 비교하여,
     * API 데이터에는 존재하지 않지만 데이터베이스에는 존재하는 JobInfo 객체들의 Position ID 리스트를 생성합니다.
     *
     * @param apiDataList API로부터 가져온 최신 JobInfo 데이터 목록
     * @param dbList 데이터베이스에서 조회한 기존 JobInfo 데이터 목록
     * @return 삭제해야 할 JobInfo 객체들의 Position ID 리스트 (API 데이터에 존재하지 않는 ID들)
     */
    protected static List<Long> makeDeleteList(List<JobInfo> apiDataList, List<JobInfo> dbList) {
        // 데이터베이스에 있는 JobInfo들의 Position ID를 Set에 저장
        Set<Long> dbIdSet = dbList.stream()
                .map(JobInfo::getPositionId)
                .collect(Collectors.toSet());

        // API에서 가져온 JobInfo들의 Position ID를 Set에 저장
        Set<Long> apiSet = apiDataList.stream()
                .map(JobInfo::getPositionId)
                .collect(Collectors.toSet());

        // 데이터베이스에 있지만 API에는 없는 Position ID들을 추려내어 삭제 대상 리스트를 만듦
        List<Long> deleteList = dbIdSet.stream()
                .filter(id-> !apiSet.contains(id))
                .toList();
        return deleteList;
    }

    /**
     * API에서 가져온 최신 데이터와 데이터베이스에 저장된 데이터를 비교하여,
     * 업데이트가 필요한 JobInfoEntity 리스트를 생성합니다.
     *
     * @param apiDataList API로부터 가져온 최신 JobInfo 데이터 목록
     * @param dbList 데이터베이스에서 조회한 기존 JobInfo 데이터 목록
     * @return 업데이트가 필요한 JobInfoEntity 객체들의 리스트
     */
    protected List<JobInfo> makeUpdateList(List<JobInfo> apiDataList, List<JobInfo> dbList) {
        // API 데이터 목록을 Position ID를 키로 하는 맵으로 변환하여, 빠른 조회가 가능하도록 함
        Map<Long, JobInfo> apiDataMap = apiDataList.stream()
                .collect(Collectors.toMap(
                        JobInfo::getPositionId,
                        jobInfo -> jobInfo,
                        (existing, replacement) -> replacement
                ));
        List<JobInfo> updateList = new ArrayList<>();

        // 데이터베이스의 JobInfo와 API 데이터를 비교하여 업데이트가 필요한 항목을 리스트에 추가
        dbList.forEach(dbJobInfo -> {
            JobInfo apiJobInfo = apiDataMap.get(dbJobInfo.getPositionId()); // 동일한 Position ID를 가진 API 데이터 조회
            // API 데이터가 존재하고, 해당 데이터가 기존 데이터와 다를 경우 업데이트 리스트에 추가
            if (apiJobInfo != null && checkNotEquals(dbJobInfo, apiJobInfo)) {
                updateList.add(apiJobInfo); // API 데이터를 엔티티로 변환하여 리스트에 추가
            }
        });


        return updateList;
    }

    /**
     * API 데이터와 데이터베이스 데이터를 비교하여 새로 저장해야 할 JobInfoEntity 리스트를 생성합니다.
     *
     * @param apiDataList API로부터 가져온 최신 JobInfo 데이터 목록
     * @param dbList 데이터베이스에서 조회한 기존 JobInfo 데이터 목록
     * @return 저장할 새로운 JobInfoEntity 객체들의 리스트
     */
    protected List<JobInfo> makeSaveList(List<JobInfo> apiDataList, List<JobInfo> dbList) {
        // 데이터베이스에 있는 JobInfo들의 Position ID를 Set에 저장
        Set<Long> dbSet = new HashSet<>();
        dbList.stream().map(JobInfo::getPositionId).forEach(dbSet::add);

        // API 데이터 중에서 DB에 존재하지 않는 JobInfo들을 필터링하여 저장할 리스트 생성
        return apiDataList.stream()
                .filter(jobInfo -> !dbSet.contains(jobInfo.getPositionId()))
                .collect(Collectors.toList());
    }

}
