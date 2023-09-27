namespace MyForstWebApplication.Models
{
    /// <summary>
    /// Прогноз погоды Forecast
    /// </summary>
    public class WeatherForecast
    {
        /// <summary>
        /// Дата измерения
        /// </summary>
        public DateTime Date { get; set; }

        /// <summary>
        /// Температура в градус Цельсия
        /// </summary>
        public int TemperatureC { get; set; }

        /// <summary>
        /// Температура в градус Форенгейта
        /// </summary>
        public int TemperatureF
        {
            get { return 32 + (int)(TemperatureC / 0.5556); }
        }
    }
}
