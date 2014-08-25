package com.coffeebland.game.phone;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.coffeebland.game.Candidate;
import com.coffeebland.input.ClickManager;
import com.coffeebland.res.Images;
import com.coffeebland.util.FontUtil;
import com.coffeebland.util.Maybe;

/**
 * Created by dagothig on 8/24/14.
 */
public class AppTindun extends PhoneApp {
    public static final float MARGIN = 8;
    public static final String DATE_BTN = "Date!";

    public static interface OnCandidateSelected {
        public void candidateSelected(Candidate candidate);
    }

    public AppTindun(Candidate candidate) {
        bg = Images.get("sprites/phone/tindun_background.png");
        arrow = Images.get("sprites/arrow.png");
        font = FontUtil.normalFont(18);
        currentCandidate = candidate;

        listeners = new ClickManager.OnClickListener[0];
    }

    private Texture bg, arrow;
    private Candidate currentCandidate;
    private BitmapFont font;
    private Maybe<OnCandidateSelected> listener = new Maybe<OnCandidateSelected>();
    private Rectangle leftArrow, rightArrow, dateBtn;

    public void setListener(OnCandidateSelected listener) {
        this.listener = new Maybe<OnCandidateSelected>(listener);

        BitmapFont.TextBounds bounds = font.getBounds(DATE_BTN);

        listeners = new ClickManager.OnClickListener[] {
                new ClickManager.OnClickListener(leftArrow = new Rectangle(0, 0, arrow.getWidth(), arrow.getHeight())) {
                    @Override
                    public void onClick() {
                        int i = currentCandidate.ordinal();
                        i--;
                        while (i < 0)
                            i += currentCandidate.values().length;
                        currentCandidate = currentCandidate.values()[i];
                    }
                },
                new ClickManager.OnClickListener(rightArrow = new Rectangle(0, 0, arrow.getWidth(), arrow.getHeight())) {
                    @Override
                    public void onClick() {
                        int i = currentCandidate.ordinal();
                        i = (i + 1) % currentCandidate.values().length;
                        currentCandidate = currentCandidate.values()[i];
                    }
                },
                new ClickManager.OnClickListener(dateBtn = new Rectangle(0, 0, bounds.width + MARGIN, bounds.height + MARGIN)) {
                    @Override
                    public void onClick() {
                        if (AppTindun.this.listener.hasValue())
                            AppTindun.this.listener.getValue().candidateSelected(currentCandidate);
                    }
                }
        };
    }

    @Override
    public void render(SpriteBatch batch, float refX, float refY, float imageScale) {
        batch.draw(bg, refX, refY, bg.getWidth() * imageScale, bg.getHeight() * imageScale);
        Texture candidateText = currentCandidate.getImage();
        batch.draw(candidateText,
                refX + (bg.getWidth() / 2 - candidateText.getWidth() / 2) * imageScale, refY + (bg.getHeight() / 2 - candidateText.getHeight() / 2) * imageScale,
                candidateText.getWidth() * imageScale, candidateText.getHeight() * imageScale
        );

        BitmapFont.TextBounds bounds = font.getBounds(currentCandidate.getName());
        font.setColor(Color.BLACK);
        font.draw(batch, currentCandidate.getName(),
                refX + (bg.getWidth() * imageScale) / 2 - bounds.width / 2,
                (refY + bg.getHeight() * imageScale - bounds.height + MARGIN)
        );

        if (listener.hasValue()) { // Selector mode
            leftArrow.x = refX + MARGIN * imageScale;
            leftArrow.y = refY + MARGIN * imageScale;
            rightArrow.x = refX + (bg.getWidth() - arrow.getWidth() - MARGIN) * imageScale;
            rightArrow.y = refY + MARGIN * imageScale;

            batch.setColor(Color.BLACK);
            batch.draw(arrow,
                    leftArrow.x, leftArrow.y,
                    arrow.getWidth() * imageScale, arrow.getHeight() * imageScale,
                    0, 0,
                    arrow.getWidth(), arrow.getHeight(),
                    false, false
            );

            batch.draw(arrow,
                    rightArrow.x, rightArrow.y,
                    arrow.getWidth() * imageScale, arrow.getHeight() * imageScale,
                    0, 0,
                    arrow.getWidth(), arrow.getHeight(),
                    true, false
            );
            batch.setColor(Color.WHITE);

            bounds = font.getBounds(DATE_BTN);
            dateBtn.x = refX + (bg.getWidth() / 2 - bounds.width / 2) * imageScale;
            dateBtn.y = refY + MARGIN;
            font.draw(batch, DATE_BTN, dateBtn.x, dateBtn.y + bounds.height + MARGIN);
        }
    }

    @Override
    public void update(float delta) {
    }
}
