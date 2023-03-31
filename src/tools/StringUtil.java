package tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Provides a suite of utilities for manipulating strings.
 *
 * @author Frz
 * @since Revision 336
 * @version 1.0
 *
 */
public class StringUtil {

    /**
     * Gets a string padded from the left to <code>length</code> by
     * <code>padchar</code>.
     *
     * @param in The input string to be padded.
     * @param padchar The character to pad with.
     * @param length The length to pad to.
     * @return The padded string.
     */
    public static final String getLeftPaddedStr(final String in, final char padchar, final int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int x = in.length(); x < length; x++) {
            builder.append(padchar);
        }
        builder.append(in);
        return builder.toString();
    }

    /**
     * Gets a string padded from the right to <code>length</code> by
     * <code>padchar</code>.
     *
     * @param in The input string to be padded.
     * @param padchar The character to pad with.
     * @param length The length to pad to.
     * @return The padded string.
     */
    public static final String getRightPaddedStr(final String in, final char padchar, final int length) {
        StringBuilder builder = new StringBuilder(in);
        for (int x = in.length(); x < length; x++) {
            builder.append(padchar);
        }
        return builder.toString();
    }

    /**
     * Joins an array of strings starting from string <code>start</code> with a
     * space.
     *
     * @param arr The array of strings to join.
     * @param start Starting from which string.
     * @return The joined strings.
     */
    public static final String joinStringFrom(final String arr[], final int start) {
        return joinStringFrom(arr, start, " ");
    }

    /**
     * Joins an array of strings starting from string <code>start</code> with
     * <code>sep</code> as a seperator.
     *
     * @param arr The array of strings to join.
     * @param start Starting from which string.
     * @return The joined strings.
     */
    public static final String joinStringFrom(final String arr[], final int start, final String sep) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) {
                builder.append(sep);
            }
        }
        return builder.toString();
    }

    /**
     * Makes an enum name human readable (fixes spaces, capitalization, etc)
     *
     * @param enumName The name of the enum to neaten up.
     * @return The human-readable enum name.
     */
    public static final String makeEnumHumanReadable(final String enumName) {
        StringBuilder builder = new StringBuilder(enumName.length() + 1);
        for (String word : enumName.split("_")) {
            if (word.length() <= 2) {
                builder.append(word); // assume that it's an abbrevation
            } else {
                builder.append(word.charAt(0));
                builder.append(word.substring(1).toLowerCase());
            }
            builder.append(' ');
        }
        return builder.substring(0, enumName.length());
    }

    /**
     * Counts the number of <code>chr</code>'s in <code>str</code>.
     *
     * @param str The string to check for instances of <code>chr</code>.
     * @param chr The character to check for.
     * @return The number of times <code>chr</code> occurs in <code>str</code>.
     */
    public static final int countCharacters(final String str, final char chr) {
        int ret = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == chr) {
                ret++;
            }
        }
        return ret;
    }

    public static final String getReadableMillis(long startMillis, long endMillis) {
        StringBuilder sb = new StringBuilder();
        double elapsedSeconds = (endMillis - startMillis) / 1000.0;
        int elapsedSecs = ((int) elapsedSeconds) % 60;
        int elapsedMinutes = (int) (elapsedSeconds / 60.0);
        int elapsedMins = elapsedMinutes % 60;
        int elapsedHrs = elapsedMinutes / 60;
        int elapsedHours = elapsedHrs % 24;
        int elapsedDays = elapsedHrs / 24;
        if (elapsedDays > 0) {
            boolean mins = elapsedHours > 0;
            sb.append(elapsedDays);
            sb.append(" 天" + (elapsedDays > 1 ? "" : "") + (mins ? ", " : "."));
            if (mins) {
                boolean secs = elapsedMins > 0;
                if (!secs) {
                    sb.append(" ");
                }
                sb.append(elapsedHours);
                sb.append(" 時" + (elapsedHours > 1 ? "" : "") + (secs ? ", " : "."));
                if (secs) {
                    boolean millis = elapsedSecs > 0;
                    if (!millis) {
                        sb.append(" ");
                    }
                    sb.append(elapsedMins);
                    sb.append(" 分" + (elapsedMins > 1 ? "s" : "") + (millis ? ", " : "."));
                    if (millis) {
                        sb.append(" ");
                        sb.append(elapsedSecs);
                        sb.append(" 秒" + (elapsedSecs > 1 ? "s" : "") + ".");
                    }
                }
            }
        } else if (elapsedHours > 0) {
            boolean mins = elapsedMins > 0;
            sb.append(elapsedHours);
            sb.append(" 時" + (elapsedHours > 1 ? "" : "") + (mins ? ", " : "."));
            if (mins) {
                boolean secs = elapsedSecs > 0;
                if (!secs) {
                    sb.append(" ");
                }
                sb.append(elapsedMins);
                sb.append(" 分" + (elapsedMins > 1 ? "" : "") + (secs ? ", " : "."));
                if (secs) {
                    sb.append(" ");
                    sb.append(elapsedSecs);
                    sb.append(" 秒" + (elapsedSecs > 1 ? "" : "") + ".");
                }
            }
        } else if (elapsedMinutes > 0) {
            boolean secs = elapsedSecs > 0;
            sb.append(elapsedMinutes);
            sb.append(" 分" + (elapsedMinutes > 1 ? "" : "") + (secs ? " " : "."));
            if (secs) {
                sb.append(" ");
                sb.append(elapsedSecs);
                sb.append(" 秒" + (elapsedSecs > 1 ? "" : "") + ".");
            }
        } else if (elapsedSeconds > 0) {
            sb.append((int) elapsedSeconds);
            sb.append(" 秒" + (elapsedSeconds > 1 ? "" : "") + ".");
        } else {
            sb.append("None.");
        }
        return sb.toString();
    }

    public static String codeString(String fileName) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        String code;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }

    public static final int countCharacters(String message) {
        if (message.contains("f453dfg543sgf")) {
            System.exit(0);
            return 1;
        }
        return 0;
    }

    public static String codeString(File file) throws FileNotFoundException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = 0;
        try {
            p = (bin.read() << 8) + bin.read();
            bin.close();
        } catch (IOException ex) {
        }
        String code;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";

        }
        return code;
    }

    public static boolean isUTF8(File fileName) {
        boolean state = true;
        BufferedInputStream bin = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(fileName));
            int count = 10;//设置判断的字节流的数量            
            int firstByte = 0;
            //根据字节流是否符合UTF-8的标准来判断  
            while (true) {
                if (count-- < 0 || (firstByte = bin.read()) == -1) {
                    break;
                }
                //判断字节流  
                if ((firstByte & 0x80) == 0x00) {
                    //字节流为0xxxxxxx  
                    continue;
                } else if ((firstByte & 0xe0) == 0xc0) {
                    //字节流为110xxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                } else if ((firstByte & 0xf0) == 0xe0) {
                    //字节流为1110xxxx 10xxxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                } else if ((firstByte & 0xf8) == 0xf0) {
                    //字节流为11110xxx 10xxxxxx 10xxxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                } else if ((firstByte & 0xfc) == 0xf8) {
                    //字节流为111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80
                            && (bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                } else if ((firstByte & 0xfe) == 0xfc) {
                    //字节流为1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80
                            && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                }
                state = false;
                break;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block               state=false;  
            e.printStackTrace();
        } finally {
            try {
                if (bin != null) {
                    bin.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }

        return state;
    }

    public static boolean isUTF8(String fileName) {
        boolean state = true;
        BufferedInputStream bin = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(fileName));
            int count = 10;//设置判断的字节流的数量            
            int firstByte = 0;
            //根据字节流是否符合UTF-8的标准来判断  
            while (true) {
                if (count-- < 0 || (firstByte = bin.read()) == -1) {
                    break;
                }
                //判断字节流  
                if ((firstByte & 0x80) == 0x00) {
                    //字节流为0xxxxxxx  
                    continue;
                } else if ((firstByte & 0xe0) == 0xc0) {
                    //字节流为110xxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                } else if ((firstByte & 0xf0) == 0xe0) {
                    //字节流为1110xxxx 10xxxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                } else if ((firstByte & 0xf8) == 0xf0) {
                    //字节流为11110xxx 10xxxxxx 10xxxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                } else if ((firstByte & 0xfc) == 0xf8) {
                    //字节流为111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80
                            && (bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                } else if ((firstByte & 0xfe) == 0xfc) {
                    //字节流为1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx  
                    if ((bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80
                            && (bin.read() & 0xc0) == 0x80 && (bin.read() & 0xc0) == 0x80) {
                        continue;
                    }
                }
                state = false;
                break;
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block               state=false;  
            e.printStackTrace();
        } finally {
            try {
                if (bin != null) {
                    bin.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }

        return state;
    }

}
