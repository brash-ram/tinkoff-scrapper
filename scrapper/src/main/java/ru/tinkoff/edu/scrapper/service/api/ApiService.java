package ru.tinkoff.edu.scrapper.service.api;


import ru.tinkoff.edu.linkParser.dto.LinkData;

public abstract class ApiService {
    protected ApiService nextService;

    /**
     * Added next service
     * @param nextService next service
     * @return next service
     */
    public ApiService setNextService(ApiService nextService) {
        this.nextService = nextService;
        return nextService;
    }

    /**
     * Get message on update current service
     * @return Message for user
     */
    public abstract String checkUpdate(LinkData linkData);
}
