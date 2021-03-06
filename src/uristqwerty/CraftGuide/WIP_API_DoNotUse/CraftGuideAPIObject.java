package uristqwerty.CraftGuide.WIP_API_DoNotUse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * WARNING: This class will be removed for the Minecraft 1.5 update!<br><br>
 *
 * Uses reflection to register itself with CraftGuide when created.
 * <br><br>
 * You do not need to use this class, it is provided only for convenience,
 * as the static method it calls will accept any Object that implements
 * zero or more of the following interfaces:
 * <li>{@link IRecipeProvider}
 */

@Deprecated
public class CraftGuideAPIObject
{
	public CraftGuideAPIObject()
	{
		try
		{
			Class c = Class.forName("uristqwerty.CraftGuide.ReflectionAPI");

			Method m = c.getMethod("registerAPIObject", Object.class);
			m.invoke(null, this);
		}
		catch(ClassNotFoundException e)
		{
		}
		catch(NoSuchMethodException e)
		{
		}
		catch(SecurityException e)
		{
		}
		catch(InvocationTargetException e)
		{
		}
		catch(IllegalAccessException e)
		{
		}
		catch(IllegalArgumentException e)
		{
		}
	}
}
