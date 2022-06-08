package io.aaron.learning.manage.factory;

import io.aaron.learning.geom.decorator.base.AbstractBoundDecorator;
import io.aaron.learning.manage.strategy.resize.*;
import io.aaron.learning.manage.strategy.resize.base.HorizontalResizeStrategy;
import io.aaron.learning.manage.strategy.resize.base.StrategyType;
import io.aaron.learning.manage.strategy.resize.base.VerticalResizeStrategy;

public class SimpleResizeStrategyFactory {

    public static HorizontalResizeStrategy getHorizontalStrategy(AbstractBoundDecorator.Handler handler, StrategyType type) {
        HorizontalResizeStrategy result = null;
        switch(handler) {
            case TOP_RIGHT:
            case RIGHT:
            case BOTTOM_RIGHT: {
                if(type == StrategyType.FREE) {
                    result = new RightResizeStrategy();
                }
                else if(type == StrategyType.EQUAL_PROPORTION) {
                    result = new EqualProportionalRightResizeStrategy();
                }
                break;
            }
            case TOP_LEFT:
            case LEFT:
            case BOTTOM_LEFT: {
                if(type == StrategyType.FREE) {
                    result = new LeftResizeStrategy();
                }
                else if(type == StrategyType.EQUAL_PROPORTION) {
                    result = new EqualProportionalLeftResizeStrategy();
                }
                break;
            }
        }
        return result;
    }

    public static VerticalResizeStrategy getVerticalStrategy(AbstractBoundDecorator.Handler handler, StrategyType type) {
        VerticalResizeStrategy result = null;
        switch(handler) {
            case TOP: {
                if(type == StrategyType.FREE) {
                    result = new TopResizeStrategy();
                }
                else if(type == StrategyType.EQUAL_PROPORTION) {
                    result = new EqualProportionalTopResizeStrategy();
                }
                break;
            }
            case BOTTOM: {
                if(type == StrategyType.FREE) {
                    result = new BottomResizeStrategy();
                }
                else if(type == StrategyType.EQUAL_PROPORTION) {
                    result = new EqualProportionalBottomResizeStrategy();
                }
                break;
            }
            case TOP_LEFT:
            case TOP_RIGHT: {
                if(type == StrategyType.FREE) {
                    result = new TopResizeStrategy();
                }
                break;
            }
            case BOTTOM_LEFT:
            case BOTTOM_RIGHT: {
                if(type == StrategyType.FREE) {
                    result = new BottomResizeStrategy();
                }
                break;
            }
        }
        return result;
    }
}
