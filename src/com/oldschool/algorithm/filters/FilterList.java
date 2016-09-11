package com.oldschool.algorithm.filters;

import com.oldschool.image.bitmap.BmpFile;
import com.oldschool.image.bitmap.exception.BadImageTypeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by MSI on 2016-09-10.
 */
public class FilterList extends Filter {

    public FilterList(BmpFile file) throws BadImageTypeException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        super(file);
    }

    public void init(String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;

        method = this.getClass().getMethod(name);
        arrayMask = (Integer[][]) method.invoke(this);
        maskSize = arrayMask.length;

        run();
    }

    public Integer[][] lowPassAverage() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
    }

    public Integer[][] lowPassSquare() {
        return new Integer[][] {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
    }

    public Integer[][] lowPassCircular() {
        return new Integer[][] {
                {0, 1, 1, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 0}
        };
    }

    public Integer[][] lowPass1() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 2, 1},
                {1, 1, 1}
        };
    }

    public Integer[][] lowPass2() {
        return new Integer[][] {
                {1, 1, 1},
                {1, 4, 1},
                {1, 1, 1}
        };
    }

    public Integer[][] lowPass3() {
        return new Integer[][] {
                {1, 1,  1},
                {1, 12, 1},
                {1, 1,  1}
        };
    }

    public Integer[][] lowPassPiramid() {
        return new Integer[][] {
                {1, 2, 3, 2, 1},
                {2, 4, 6, 4, 2},
                {3, 6, 9, 6, 3},
                {2, 4, 6, 4, 2},
                {1, 2, 3, 2, 1}
        };
    }

    public Integer[][] lowPassConical() {
        return new Integer[][] {
                {0, 0, 1, 0, 0},
                {0, 4, 4, 4, 0},
                {1, 2, 5, 2, 1},
                {0, 4, 4, 4, 0},
                {0, 0, 1, 0, 0}
        };
    }

    public Integer[][] lowPassGauss1() {
        return new Integer[][] {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
    }

    public Integer[][] lowPassGauss2() {
        return new Integer[][] {
                {1, 1, 2, 1, 1},
                {1, 2, 4, 2, 1},
                {2, 4, 8, 4, 2},
                {1, 2, 4, 2, 1},
                {1, 1, 2, 1, 1}
        };
    }

    public Integer[][] lowPassGauss3() {
        return new Integer[][] {
                {0, 1, 2, 1, 0},
                {1, 4, 8, 4, 1},
                {2, 8, 16, 8, 2},
                {1, 4, 8, 4, 1},
                {0, 1, 2, 1, 0}
        };
    }

    public Integer[][] lowPassGauss4() {
        return new Integer[][] {
                {1, 4, 7, 4, 1},
                {4, 16, 26, 16, 4},
                {7, 26, 41, 26, 7},
                {4, 16, 26, 16, 4},
                {1, 4, 7, 4, 1}
        };
    }

    public Integer[][] lowPassGauss5() {
        return new Integer[][] {
                {1, 1, 2, 2, 2, 1, 1},
                {1, 2, 2, 4, 2, 2, 1},
                {2, 2, 4, 8, 4, 2, 2},
                {2, 4, 8, 16, 8, 4, 2},
                {2, 2, 4, 8, 4, 2, 2},
                {1, 2, 2, 4, 2, 2, 1},
                {1, 1, 2, 2, 2, 1, 1}
        };
    }

    public Integer[][] highPassDeleteAverage() {
        return new Integer[][] {
                {-1, -1, -1},
                {-1,  9, -1},
                {-1, -1, -1}
        };
    }

    public Integer[][] highPass1() {
        return new Integer[][] {
                {0,  -1,  0},
                {-1,  5, -1},
                {0,  -1,  0}
        };
    }

    public Integer[][] highPass2() {
        return new Integer[][] {
                {1,  -2,  1},
                {-2,  5, -2},
                {1,  -2,  1}
        };
    }

    public Integer[][] highPass3() {
        return new Integer[][] {
                {0,   -1,  0},
                {-1,  20, -1},
                {0,   -1,  0}
        };
    }

    public Integer[][] gradientDirectionalEast() {
        return new Integer[][] {
                {-1, 1,  1},
                {-1, -2, 1},
                {-1, 1,  1}
        };
    }

    public Integer[][] gradientDirectionalSoutheast() {
        return new Integer[][] {
                {-1, -1, 1},
                {-1, -2, 1},
                { 1,  1, 1}
        };
    }

    public Integer[][] gradientDirectionalSouth() {
        return new Integer[][] {
                {-1, -1, -1},
                { 1, -2,  1},
                { 1,  1,  1}
        };
    }

    public Integer[][] gradientDirectionalSouthwest() {
        return new Integer[][] {
                { 1, -1, -1},
                { 1, -2, -1},
                { 1,  1,  1}
        };
    }

    public static Integer[][] gradientDirectionalWest() {
        return new Integer[][] {
                { 1,  1, -1},
                { 1, -2, -1},
                { 1,  1, -1}
        };
    }

    public static Integer[][] gradientDirectionalNorthwest() {
        return new Integer[][] {
                { 1,  1,  1},
                { 1, -2, -1},
                { 1, -1, -1}
        };
    }

    public static Integer[][] gradientDirectionalNorth() {
        return new Integer[][] {
                { 1,  1,  1},
                { 1, -2,  1},
                {-1, -1, -1}
        };
    }

    public static Integer[][] gradientDirectionalNortheast() {
        return new Integer[][] {
                { 1,  1,  1},
                {-1, -2,  1},
                {-1, -1,  1}
        };
    }
}
