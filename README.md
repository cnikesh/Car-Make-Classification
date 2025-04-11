

#🚗 Car Make, Model & Year Classification with Angle Detection 🔍

This project aims to create a robust and efficient real-time car classification system, capable of processing live camera input and providing instantaneous predictions on the Year, Model, and Make of cars. By leveraging deep learning techniques, the system is designed for practical applications such as:

    🚓 Crime scene investigations

    🚦 Traffic monitoring

    🏭 Inventory management in automotive sectors

Incorporating angle detection into the pipeline further enhances the prediction accuracy, making the system reliable in diverse environments and viewing conditions.
Methodology

The system employs Convolutional Neural Networks (CNNs) as its core architecture, which are highly effective in extracting and learning visual patterns from images.
The dataset is carefully structured with class-specific folders and angle-specific subfolders to guide the model in learning not only the characteristics of different car makes, models, and years but also their variations based on viewing angles.

Key highlights of the approach:

    📂 Custom hierarchical dataset — organized by make, model, year, and angle for better generalization.

    🧩 CNN-based architecture — learns hierarchical representations for robust classification.

    🎥 Real-time processing capability — suitable for deployment with live camera feeds.


## Notebooks Overview

### 1. DL_Project Angle Detection.ipynb
- **Purpose**: Detects the angle (front, rear, side, etc.) of a car from an image.
- **Contents**:
  - Data loading and preprocessing
  - Data augmentation techniques
  - Model architecture using Convolutional Neural Networks (CNNs)
  - Model training and evaluation
  - Angle classification results and accuracy metrics

### 2. DL_Project Angle based classification models.ipynb
- **Purpose**: Builds car make classification models that utilize the angle of the car for more accurate predictions.
- **Contents**:
  - Uses angle detection results to enhance make classification
  - Model training for car make prediction
  - Evaluation of angle-based classification models
  - Analysis of accuracy improvements when combining angle data

## Features

- 📊 **Angle-based classification:** Improve prediction accuracy by incorporating car angle information.
- 🧩 **Custom CNN models:** Tailored deep learning architectures for image classification.
- 📲 **Android Application:** Implemented as an mobile application for real-world usage.

## Installation & Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/cnikesh/Car-Make-Classification.git
   cd Car-Make-Classification
   ```
2. Add your dataset in the project directory (make sure to update the paths in the notebooks accordingly).

3. Run the notebooks step by step to train and evaluate the models.

## Results

- Achieved high accuracy for both angle detection and make classification models.
- Angle-based approach demonstrated improved classification performance over baseline.

## Future Work

- 📦 Integrate a pre-trained model for faster and more accurate results.
- 🖥️ Deploy the models as an API or web service.
- 🚗 Expand dataset to include more car makes and angles.
- 📊 Add confusion matrix and ROC curve visualizations.

## Contributing

Contributions are welcome! Feel free to fork the repository and submit pull requests.


## Author

**Nikesh Chithambaram**  
[LinkedIn](https://www.linkedin.com/in/nikeshchithambaram/) • [GitHub](https://github.com/cnikesh)
