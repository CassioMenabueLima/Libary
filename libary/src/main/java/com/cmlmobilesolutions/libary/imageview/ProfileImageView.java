package com.cmlmobilesolutions.libary.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class ProfileImageView extends android.support.v7.widget.AppCompatImageView {
    public ProfileImageView(Context context) {
        super(context);
    }

    public ProfileImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        // pegamos o drawable e transformamos em Bitmap
        Bitmap b = convertToBitmap(drawable, drawable.getBounds().width() ,drawable.getBounds().height());
        // pegamos o tamanho da imagem
        int w = getWidth(), h = getHeight();
        // geramos a imagem redonda..
        Bitmap roundBitmap = getCroppedBitmap(b, w);
        //desenhamos a imagem
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }


    /**
     * Responsavel por Converter um Drawable em Bitmap
     */
    public Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);
        return mutableBitmap;
    }

    /**
     * Disponibiliza uma imagem redonda
     */
    public Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        // Ajustamos o tamanho, se necessario
        if (bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        // Nova Imagem...
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        // Canvas onde iremos desenhar
        Canvas canvas = new Canvas(output);
        //  Configuramos o Paint...
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,  sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }


}
