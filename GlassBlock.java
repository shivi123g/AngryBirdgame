package io.github.bird.game.Objects;

import io.github.bird.game.Objects.Block;

public class GlassBlock extends Block {

    public GlassBlock(float x, float y) {
        super(x, y, 1, "glass.png");
    }

    @Override
    protected void destroy() {
        super.destroy();
    }
}
