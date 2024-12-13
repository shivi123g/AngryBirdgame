package io.github.bird.game.Objects;

public class SteelBlock extends io.github.bird.game.Objects.Block {

    public SteelBlock(float x, float y) {
        super(x, y, 5, "steel.png");
    }

    @Override
    protected void destroy() {
        super.destroy();
    }
}
