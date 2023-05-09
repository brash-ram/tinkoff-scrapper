package ru.tinkoff.edu.scrapper.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.linkParser.dto.LinkData;
import ru.tinkoff.edu.linkParser.dto.LinkDataGithub;
import ru.tinkoff.edu.linkParser.enums.Site;
import ru.tinkoff.edu.scrapper.client.GitHubClient;
import ru.tinkoff.edu.scrapper.dto.response.GitHubRepositoryInfoResponse;

@Service
@RequiredArgsConstructor
class GitHubApiService extends ApiService {

    private final GitHubClient gitHubClient;

    @Override
    public String checkUpdate(LinkData linkData) {
        if (linkData.getSite().equals(Site.GITHUB)) {
            LinkDataGithub linkDataGithub = (LinkDataGithub) linkData;
            GitHubRepositoryInfoResponse infoResponse = gitHubClient.getGitHubRepositoryInfo(
                    linkDataGithub.getUser(),
                    linkDataGithub.getRepository()
            );
            StringBuilder sb = new StringBuilder();
            sb.append("Обновление [репозитория](")
                    .append(linkDataGithub.getUrl())
                    .append(")\n")
                    .append("Последнее обновление: ")
                    .append(infoResponse.updated_at().toString())
                    .append("\n")
                    .append("Последний push: ")
                    .append(infoResponse.pushed_at().toString())
                    .append("\n");
            return sb.toString();
        } else if (nextService != null) {
            return nextService.checkUpdate(linkData);
        } else {
            return null;
        }
    }
}
