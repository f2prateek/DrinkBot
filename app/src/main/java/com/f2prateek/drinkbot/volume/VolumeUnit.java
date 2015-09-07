package com.f2prateek.drinkbot.volume;

/**
 * A {@code VolumeUnit} represents a volume at a given unit of granularity which can be converted
 * into ml. A {@code VolumeUnit} does not maintain volume size information, but only helps use
 * volume size representations that may be maintained separately across various contexts.
 *
 * @see DecimalByteUnit
 * @see BinaryByteUnit
 * @see BitUnit
 */
public interface VolumeUnit {
  /**
   * Converts the given volume in the given unit to milliliters. Conversions with arguments that
   * would numerically overflow saturate to {@code Long.MIN_VALUE} if negative or {@code
   * Long.MAX_VALUE} if positive.
   *
   * @param volume the volume
   * @return the converted volume, or {@code Long.MIN_VALUE} if conversion would negatively
   * overflow, or {@code Long.MAX_VALUE} if it would positively overflow.
   */
  long toMilliliters(long volume);
}
