package ru.tinkoff.edu.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tinkoff.edu.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.scrapper.dto.response.ListLinksResponse;

public interface ApiController {
    /**
     * DELETE /links : Убрать отслеживание ссылки
     *
     * @param tgChatId  (required)
     * @param removeLinkRequest  (required)
     * @return Ссылка успешно убрана (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     *         or Ссылка не найдена (status code 404)
     */
    @Operation(
            operationId = "linksDelete",
            summary = "Убрать отслеживание ссылки",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/links",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    ResponseEntity<LinkResponse> linksDelete(
            @NotNull @Parameter(name = "Tg-Chat-Id", required = true, in = ParameterIn.HEADER) @RequestHeader(value = "Tg-Chat-Id") Long tgChatId,
            @Parameter(name = "RemoveLinkRequest", required = true) @Valid @RequestBody RemoveLinkRequest removeLinkRequest
    );


    /**
     * GET /links : Получить все отслеживаемые ссылки
     *
     * @param tgChatId  (required)
     * @return Ссылки успешно получены (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     */
    @Operation(
            operationId = "linksGet",
            summary = "Получить все отслеживаемые ссылки",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/links",
            produces = { "application/json" }
    )
    ResponseEntity<ListLinksResponse> linksGet(
            @NotNull @Parameter(name = "Tg-Chat-Id", required = true, in = ParameterIn.HEADER) @RequestHeader(value = "Tg-Chat-Id") Long tgChatId
    );


    /**
     * POST /links : Добавить отслеживание ссылки
     *
     * @param tgChatId  (required)
     * @param addLinkRequest  (required)
     * @return Ссылка успешно добавлена (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     */
    @Operation(
            operationId = "linksPost",
            summary = "Добавить отслеживание ссылки",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/links",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    ResponseEntity<LinkResponse> linksPost(
            @NotNull
            @Parameter(name = "Tg-Chat-Id", required = true, in = ParameterIn.HEADER)
            @RequestHeader(value = "Tg-Chat-Id")
            Long tgChatId,
            @Parameter(name = "AddLinkRequest", required = true)
            @Valid
            @RequestBody
            AddLinkRequest addLinkRequest
    );

    /**
     * DELETE /tg-chat/{id} : Удалить чат
     *
     * @param id  (required)
     * @return Чат успешно удалён (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     *         or Чат не существует (status code 404)
     */
    @Operation(
            operationId = "tgChatIdDelete",
            summary = "Удалить чат",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Чат успешно удалён"),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Чат не существует", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/tg-chat/{id}",
            produces = { "application/json" }
    )
    ResponseEntity<Void> tgChatIdDelete(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );


    /**
     * POST /tg-chat/{id} : Зарегистрировать чат
     *
     * @param id  (required)
     * @return Чат зарегистрирован (status code 200)
     *         or Некорректные параметры запроса (status code 400)
     */
    @Operation(
            operationId = "tgChatIdPost",
            summary = "Зарегистрировать чат",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/tg-chat/{id}",
            produces = { "application/json" }
    )
    ResponseEntity<Void> tgChatIdPost(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    );
}
