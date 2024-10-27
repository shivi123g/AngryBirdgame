package io.github.bird.game.Objects;

public class WoodBlock extends io.github.bird.game.Objects.Block {

    public WoodBlock(float x, float y) {
        super(x, y, 3, "wood.png");
    }

    @Override
    protected void destroy() {
        super.destroy();
    }
}
