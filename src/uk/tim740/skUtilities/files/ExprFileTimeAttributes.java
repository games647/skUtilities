package uk.tim740.skUtilities.files;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.tim740.skUtilities.skUtilities;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

/**
 * Created by tim740 on 21/03/2016
 */
public class ExprFileTimeAttributes extends SimpleExpression<Number>{
	private Expression<String> path;
    private int type;

	@Override
	@Nullable
	protected Number[] get(Event arg0) {
        File pth = new File("plugins" + File.separator + path.getSingle(arg0).replaceAll("/", File.separator));
        if (pth.exists()){
            try {
                if (type == 0) {
                    return new Number[]{pth.lastModified() /1000};
                }else if (type == 1){
                    return new Number[]{Files.readAttributes(Paths.get(pth.toString()), BasicFileAttributes.class).creationTime().to(TimeUnit.SECONDS)};
                }else{
                    return new Number[]{Files.readAttributes(Paths.get(pth.toString()), BasicFileAttributes.class).lastAccessTime().to(TimeUnit.SECONDS)};
                }
            } catch (IOException e) {
                skUtilities.prEW(e.getMessage(), getClass().getSimpleName(), 0);
                return null;
            }
        }else{
            skUtilities.prEW("'" + pth + "' doesn't exist!", getClass().getSimpleName(), 0);
            return null;
        }
	}

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        path = (Expression<String>) arg0[0];
        type = arg3.mark;
        return true;
    }
    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
    @Override
    public boolean isSingle() {
        return true;
    }
    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return getClass().getName();
    }
}