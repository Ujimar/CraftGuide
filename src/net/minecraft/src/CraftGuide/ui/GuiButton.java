package net.minecraft.src.CraftGuide.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.src.CraftGuide.ui.Rendering.GuiTexture;
import net.minecraft.src.CraftGuide.ui.Rendering.IRenderable;
import net.minecraft.src.CraftGuide.ui.Rendering.TexturedRect;

public class GuiButton extends GuiElement
{
	protected enum ButtonState
	{
		UP,
		UP_OVER,
		DOWN,
		DOWN_OVER,
	}
	
	private List<IButtonListener> buttonListeners = new LinkedList<IButtonListener>();
	protected Map<ButtonState, IRenderable> stateImages = new HashMap<ButtonState, IRenderable>();
	
	private ButtonState currentState = ButtonState.UP;


	public GuiButton(int x, int y, int width, int height, GuiTexture texture, int u, int v)
	{
		this(x, y, width, height, texture, u, v, width, 0);
	}
	
	public GuiButton(int x, int y, int width, int height, GuiTexture texture, int u, int v, int dx, int dy)
	{
		super(x, y, width, height);
		
		int yOffset = 0;
		int xOffset = 0;
		for(ButtonState state: ButtonState.values())
		{
			IRenderable image = new TexturedRect(0, 0, width, height, texture, u + xOffset, v + yOffset);
			
			stateImages.put(state, image);
			xOffset += dx;
			yOffset += dy;
		}
	}

	protected GuiButton(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void draw()
	{
		render(stateImages.get(currentState));
		
		super.draw();
	}
	
	@Override
	public void mouseMoved(int x, int y)
	{
		updateState(isMouseOver(x, y), isHeld());
	}

	@Override
	public void mousePressed(int x, int y)
	{
		if(isMouseOver(x, y))
		{
			if(!isHeld())
			{
				sendButtonEvent(IButtonListener.Event.PRESS);
			}
			updateState(true, true);
		}
	}

	@Override
	public void mouseReleased(int x, int y)
	{
		if(isHeld())
		{
			sendButtonEvent(IButtonListener.Event.RELEASE);
		}
		
		updateState(isMouseOver(x, y), false);
	}
	
	private void updateState(boolean over, boolean held)
	{
		currentState = 
			held?
				over?
					ButtonState.DOWN_OVER
				:
					ButtonState.DOWN
			:
				over?
					ButtonState.UP_OVER
				:
					ButtonState.UP;
	}
	
	protected boolean isHeld()
	{
		return currentState == ButtonState.DOWN || currentState == ButtonState.DOWN_OVER;
	}
	
	protected boolean isOver()
	{
		return currentState == ButtonState.UP_OVER || currentState == ButtonState.DOWN_OVER;
	}
	
	public void addButtonListener(IButtonListener listener)
	{
		buttonListeners.add(listener);
	}
	
	private void sendButtonEvent(IButtonListener.Event eventType)
	{
		for(IButtonListener listener: buttonListeners)
		{
			listener.onButtonEvent(this, eventType);
		}
	}
}