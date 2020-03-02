package io.github.cgkenlee.open_rps.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cgkenlee.open_rps.game.*;
import io.github.cgkenlee.open_rps.game.model.Game;
import io.github.cgkenlee.open_rps.game.restdocs.snippet.GameModeSnippet;
import io.github.cgkenlee.open_rps.game.restdocs.snippet.GameStateSnippet;
import io.github.cgkenlee.open_rps.game.restdocs.snippet.HandSnippet;
import io.github.cgkenlee.open_rps.game.service.GameService;
import io.github.cgkenlee.open_rps.game.util.GameOutcomeUtil;
import io.github.cgkenlee.open_rps.game.util.HandUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.hypermedia.HypermediaDocumentation;
import org.springframework.restdocs.hypermedia.LinkDescriptor;
import org.springframework.restdocs.hypermedia.LinksSnippet;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static io.github.cgkenlee.open_rps.restdocs.payload.JsonFieldTypeExtra.STRING_ENUM;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@link GameController} unit test
 *
 * @author cgkenlee
 * @version 0.0.1, 2020-02-29
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@Tag("CITest")
public class GameControllerTest {
    private static final LinkDescriptor selfLinkDescriptor = linkWithRel("self").description("Canonical self link");
    private static final LinkDescriptor cheatLinkDescriptor = linkWithRel("cheat").description("Cheat controller link");
    private static final FieldDescriptor[] gameDtoResponseFieldDescriptors = {
            fieldWithPath("id").type(STRING).description("Game's id"),
            fieldWithPath("mode").type(STRING_ENUM).description("Game's mode enum"),
            fieldWithPath("opponentHand").type(STRING_ENUM).description("Opponent's hand enum if cheated or played, _SECRET_ otherwise"),
            fieldWithPath("playerHand").type(STRING_ENUM).description("Player's hand enum if played, _NOT_PLAYED_ otherwise"),
            fieldWithPath("state").type(STRING_ENUM).description("Game's state enum"),
            fieldWithPath("cheated").type(BOOLEAN).description("Whether the game is cheated (opponent's hand revealed)")
    };
    private static final LinksSnippet selfLink = HypermediaDocumentation.links(halLinks(), selfLinkDescriptor);
    private static final ResponseFieldsSnippet gameDtoResponseFields = relaxedResponseFields(gameDtoResponseFieldDescriptors);

    @MockBean
    private GameService gameService;

    private MockMvc mockMvc;

    private Game generateGame() {
        return new Game();
    }

    private Game generateCheatedGame() {
        Game cheatedGame = new Game();
        cheatedGame.setCheated(true);

        return cheatedGame;
    }

    private Game generatePlayedGame() {
        Game playedGame = new Game();
        playedGame.setPlayerHand(HandUtil.generateRandomHand());
        playedGame.setState(
                GameOutcomeUtil.resolve(
                        playedGame.getMode(),
                        playedGame.getPlayerHand(),
                        playedGame.getOpponentHand()));

        return playedGame;
    }

    @BeforeEach
    void setup(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                        .and()
                        .snippets()
                        .withDefaults(
                                httpRequest(),
                                httpResponse())
                        .withAdditionalDefaults(
                                new GameModeSnippet(),
                                new GameStateSnippet(),
                                new HandSnippet()))
                .build();
    }

    @AfterEach
    void cleanup() {
        Mockito.reset(gameService);
    }

    @Test
    @DisplayName("#createGame")
    void createGame() throws Exception {
        Mockito.when(gameService.createGame(any(GameMode.class))).thenReturn(generateGame());

        mockMvc.perform(post("/games?mode=STANDARD"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mode", equalTo("STANDARD")))
                .andDo(document(
                        "games/{method-name}",
                        requestParameters(
                                parameterWithName("mode").description("Game mode enum")),
                        gameDtoResponseFields,
                        selfLink.and(cheatLinkDescriptor)));
    }

    @Test
    @DisplayName("#getGame")
    void getGame() throws Exception {
        RestDocumentationResultHandler getGamesBaseHandler = document(
                "games/{method-name}/{step}",
                pathParameters(
                        parameterWithName("id").description("Game's id")),
                gameDtoResponseFields);

        // Case 1: Not played game
        Game game = generateGame();

        Mockito.when(gameService.getGame(any(UUID.class))).thenReturn(game);

        mockMvc.perform(get("/games/{id}", game.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opponentHand", equalTo("SECRET")))
                .andExpect(jsonPath("$.playerHand", equalTo("NOT_PLAYED")))
                .andDo(document(
                        "games/{method-name}/{step}",
                        pathParameters(
                                parameterWithName("id").description("Game's id")),
                        gameDtoResponseFields,
                        selfLink.and(cheatLinkDescriptor)));

        // Case 2: Cheated game
        Game cheatedGame = generateCheatedGame();

        Mockito.when(gameService.getGame(any())).thenReturn(cheatedGame);

        mockMvc.perform(get("/games/{id}", cheatedGame.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opponentHand", equalTo(cheatedGame.getOpponentHand().name())))
                .andExpect(jsonPath("$.playerHand", equalTo("NOT_PLAYED")))
                .andDo(document(
                        "games/{method-name}/{step}",
                        pathParameters(
                                parameterWithName("id").description("Game's id")),
                        gameDtoResponseFields,
                        selfLink));

        // Case 3: Played game
        Game playedGame = generatePlayedGame();

        Mockito.when(gameService.getGame(any())).thenReturn(playedGame);

        mockMvc.perform(get("/games/{id}", playedGame.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opponentHand", equalTo(playedGame.getOpponentHand().name())))
                .andExpect(jsonPath("$.playerHand", equalTo(playedGame.getPlayerHand().name())))
                .andExpect(jsonPath("$.state", equalTo(GameOutcomeUtil.resolve(playedGame.getMode(), playedGame.getPlayerHand(), playedGame.getOpponentHand()).name())))
                .andDo(document(
                        "games/{method-name}/{step}",
                        pathParameters(
                                parameterWithName("id").description("Game's id")),
                        gameDtoResponseFields,
                        selfLink));
    }

    @Test
    @DisplayName("#updateGame")
    void updateGame() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Game requestGame = generateGame();
        requestGame.setMode(GameMode.STANDARD);
        requestGame.setPlayerHand(HandUtil.generateRandomHand());

        String requestBodyString = objectMapper.writeValueAsString(requestGame);

        // clone the requestGame and change state to mimic the service behaviour
        Game playedGame = objectMapper.readValue(requestBodyString, Game.class);
        playedGame.setState(
                GameOutcomeUtil.resolve(
                        playedGame.getMode(),
                        playedGame.getPlayerHand(),
                        playedGame.getOpponentHand()));

        Mockito.when(gameService.updateGame(any(UUID.class), any(Game.class))).thenReturn(playedGame);

        mockMvc.perform(put("/games/{id}", requestGame.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opponentHand", equalTo(requestGame.getOpponentHand().name())))
                .andExpect(jsonPath("$.playerHand", equalTo(requestGame.getPlayerHand().name())))
                .andExpect(jsonPath("$.state", equalTo(playedGame.getState().name())))
                .andDo(document("games/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("Game's id")),
                        relaxedRequestFields(
                                fieldWithPath("mode").type(STRING_ENUM).description("Game's mode enum").optional(),
                                fieldWithPath("playerHand").type(STRING_ENUM).description("Hand enum").optional()),
                        gameDtoResponseFields,
                        selfLink.and(cheatLinkDescriptor.description("Cheat controller link if hand was not played and the game is not already cheated").optional())));
    }

    @Test
    @DisplayName("#cheat")
    void cheat() throws Exception {
        Game cheatedGame = generateCheatedGame();

        Mockito.when(gameService.markGameAsCheated(any(UUID.class))).thenReturn(cheatedGame);

        mockMvc.perform(post("/games/{id}/cheat", cheatedGame.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opponentHand", equalTo(cheatedGame.getOpponentHand().name())))
                .andDo(document("games/{method-name}",
                        gameDtoResponseFields,
                        selfLink));
    }

    @Test
    @DisplayName("#deleteGame")
    void deleteGame() throws Exception {
        Mockito.doNothing().when(gameService).deleteGame(any(UUID.class));

        mockMvc.perform(delete(String.format("/games/%s", UUID.randomUUID())))
                .andExpect(status().isNoContent())
                .andDo(document("games/{method-name}"));
    }
}
